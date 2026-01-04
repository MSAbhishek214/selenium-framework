import json
import re
from pathlib import Path
from datetime import datetime

from llm_local import LocalLLM


BASE_DIR = Path(__file__).resolve().parents[1]
PROMPT_PATH = BASE_DIR / "prompts" / "triage_prompt.txt"
OUTPUT_DIR = BASE_DIR / "output"


def validate_rulebook(analysis: dict):
    if "rulebook" not in analysis:
        raise RuntimeError(
            "Rulebook data missing. Triage requires enriched analysis."
        )


def enforce_hard_stop_constraints(analysis: dict):
    rb = analysis["rulebook"]

    if rb.get("hard_stop") is True:
        analysis["_constraints"] = {
            "forbidden_actions": ["IGNORE"],
            "minimum_priority": "HIGH"
        }


def clean_llm_output(raw_output: str) -> str:
    cleaned = re.sub(r"```(?:json)?", "", raw_output)
    cleaned = cleaned.replace("```", "").strip()

    match = re.search(r"\{.*\}", cleaned, re.DOTALL)
    if not match:
        raise RuntimeError("No JSON object found in LLM output")

    return match.group(0)


def validate_decision(decision: dict, analysis: dict):
    constraints = analysis.get("_constraints", {})

    forbidden = constraints.get("forbidden_actions", [])
    min_priority = constraints.get("minimum_priority")

    if decision["action"] in forbidden:
        raise RuntimeError(
            f"Illegal action '{decision['action']}' for hard-stop failure"
        )

    if min_priority:
        order = ["LOW", "MEDIUM", "HIGH", "CRITICAL"]
        if order.index(decision["priority"]) < order.index(min_priority):
            raise RuntimeError(
                f"Priority '{decision['priority']}' below required minimum '{min_priority}'"
            )


def run_triage(enriched_analysis_path: Path):
    analysis = json.loads(enriched_analysis_path.read_text(encoding="utf-8"))

    validate_rulebook(analysis)
    enforce_hard_stop_constraints(analysis)

    prompt = PROMPT_PATH.read_text(encoding="utf-8")
    constraints = analysis.get("_constraints", {})

    full_prompt = f"""
{prompt}

Failure Analysis Input:
{json.dumps(analysis, indent=2)}

Decision Constraints:
{json.dumps(constraints, indent=2)}
"""

    llm = LocalLLM()
    raw_output = llm.generate(full_prompt)
    json_text = clean_llm_output(raw_output)

    decision = json.loads(json_text)
    validate_decision(decision, analysis)

    timestamp = datetime.now().strftime("%Y-%m-%d_%H-%M-%S")
    run_dir = OUTPUT_DIR / timestamp
    run_dir.mkdir(parents=True, exist_ok=True)

    output_path = run_dir / "decision.json"
    output_path.write_text(json.dumps(decision, indent=2), encoding="utf-8")

    print(f"Saved to {output_path}")


if __name__ == "__main__":
    sample_input = BASE_DIR / "sample-input" / "enriched1_analysis.json"
    run_triage(sample_input)
