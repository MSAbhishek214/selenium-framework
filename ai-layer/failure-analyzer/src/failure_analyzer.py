import argparse
import json
import sys
from pathlib import Path
import subprocess

from llm_local import LocalLLM

ANALYSIS_VERSION = "1.0"


def read_required(path: Path, name: str) -> str:
    if not path.exists():
        print(f"❌ Missing required file: {name}", file=sys.stderr)
        sys.exit(1)
    return path.read_text(encoding="utf-8")


def parse_json_strict(output: str) -> dict:
    lines = output.strip().splitlines()

    # Case 1: fenced JSON (``` or ```json)
    if lines and lines[0].startswith("```"):
        if not (len(lines) >= 2 and lines[-1].startswith("```")):
            raise RuntimeError("Malformed fenced JSON from LLM")

        json_text = "\n".join(lines[1:-1]).strip()

    # Case 2: unfenced JSON (allowed, but must be pure JSON)
    else:
        json_text = output.strip()

    try:
        return json.loads(json_text)
    except json.JSONDecodeError:
        print("❌ LLM returned invalid JSON", file=sys.stderr)
        print(json_text, file=sys.stderr)
        sys.exit(1)


def build_prompt(raw_log: str, metadata: dict) -> str:
    return f"""
You are a failure analyzer.

You will be given:
1. A raw failure log (authoritative)
2. Execution metadata (context only)

Rules:
- Do NOT guess beyond the log
- Be concise
- Output JSON ONLY
- Follow the exact schema

Schema:
{{
  "failureType": string,
  "summary": string,
  "suspectedCause": string,
  "suggestedAction": string,
  "confidence": number between 0 and 1
}}

Raw failure log:
{raw_log}

Metadata:
{json.dumps(metadata, indent=2)}
""".strip()


def normalize_confidence(raw_confidence) -> dict:
    """
    Normalize LLM confidence with explicit governance.

    Returns both:
    - aiConfidenceRaw: model-emitted value (auditing, research)
    - aiConfidence: bounded, system-safe representation
    """

    try:
        raw = float(raw_confidence)
    except (TypeError, ValueError):
        raw = 0.5  # neutral fallback for malformed output

    # Clamp to [0, 1] first (defensive)
    raw = max(0.0, min(1.0, raw))

    # Governance bounds (semantic, not statistical)
    LOWER_BOUND = 0.05
    UPPER_BOUND = 0.95

    bounded = max(LOWER_BOUND, min(UPPER_BOUND, raw))

    return {"aiConfidenceRaw": round(raw, 4), "aiConfidence": round(bounded, 4)}


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--input-dir", required=True)
    parser.add_argument("--output-dir", required=True)
    args = parser.parse_args()

    input_dir = Path(args.input_dir)
    output_dir = Path(args.output_dir)

    raw_log = read_required(input_dir / "raw.log", "raw.log")
    metadata = json.loads(read_required(input_dir / "metadata.json", "metadata.json"))

    prompt = build_prompt(raw_log, metadata)

    llm = LocalLLM()
    llm_output = llm.generate(prompt)

    analysis = parse_json_strict(llm_output)

    # clamp confidence to 0.05 - 0.95
    confidence_block = normalize_confidence(analysis.get("confidence"))

    # update the analysis with the clamped confidence
    analysis.update(confidence_block)
    analysis.pop("confidence", None)

    final_output = {"analysisVersion": ANALYSIS_VERSION, **analysis}

    output_path = output_dir / "analysis.json"
    output_path.write_text(json.dumps(final_output, indent=2), encoding="utf-8")

    print(f"✅ analysis.json written to {output_path}")


if __name__ == "__main__":
    main()
