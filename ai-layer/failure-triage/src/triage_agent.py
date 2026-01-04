import json
import re
from datetime import datetime
from pathlib import Path
from llm_local import LocalLLM

BASE_DIR = Path(__file__).resolve().parents[1]  # Resolves to failure-triage directory
PROMPT_PATH = BASE_DIR / "prompts" / "triage_prompt.txt"
OUTPUT_DIR = BASE_DIR / "output"


def load_prompt() -> str:
    return PROMPT_PATH.read_text(encoding="utf-8")


def clean_llm_output(raw_output: str) -> str:
    """
    Removes markdown code fences and extracts JSON.
    """
    cleaned = re.sub(r"```(?:json)?", "", raw_output)
    cleaned = cleaned.replace("```", "").strip()

    match = re.search(r"\{.*\}", cleaned, re.DOTALL)
    if not match:
        raise ValueError("No JSON object found in LLM output")

    return match.group(0)


def run_triage(analysis_path: Path) -> None:
    analysis_data = json.loads(analysis_path.read_text(encoding="utf-8"))
    prompt_template = load_prompt()

    full_prompt = f"""
{prompt_template}

Failure Analysis Input:
{json.dumps(analysis_data, indent=2)}
"""

    llm = LocalLLM()
    raw_output = llm.generate(full_prompt)
    json_output = clean_llm_output(raw_output)

    decision = json.loads(json_output)

    timestamp = datetime.now().strftime("%Y-%m-%d_%H-%M-%S")
    run_dir = OUTPUT_DIR / timestamp
    run_dir.mkdir(parents=True, exist_ok=True)

    output_path = run_dir / "decision.json"
    output_path.write_text(json.dumps(decision, indent=2), encoding="utf-8")

    print(f"Saved to {output_path}")


if __name__ == "__main__":
    sample_input = BASE_DIR / "sample-input" / "analysis_env.json"
    run_triage(sample_input)
