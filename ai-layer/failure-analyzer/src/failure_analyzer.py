import argparse
from pathlib import Path
from llm_local import LocalLLM
import json
from datetime import datetime
from pathlib import Path
import re


PROMPT_TEMPLATE = Path(__file__).parent.parent / "prompts" / "failure_prompt.txt"
OUTPUT_DIR = Path(__file__).parent.parent / "output"


def load_template() -> str:
    return Path(PROMPT_TEMPLATE).read_text()


def load_failures(path: Path) -> str:
    return path.read_text()


def analyze_failures(failure_text: str) -> str:
    template = load_template()
    prompt = template.format(failure_logs=failure_text)

    llm = LocalLLM("qwen2.5-coder:1.5b")   # change to your model tag if different
    output = llm.generate(prompt)
    return output


def clean_json_output(data: str) -> str:
    """
    Clean the LLM output by removing code fences, markdown syntax,
    and extracting only the JSON body.
    """
    # remove triple backticks and optional 'json'
    data = re.sub(r"```json|```", "", data).strip()

    # attempt to extract the first JSON object
    match = re.search(r"\{.*\}", data, re.DOTALL)
    if match:
        return match.group(0)

    return data  # return cleaned string even if not perfect


def save_output(data: str):
    cleaned = clean_json_output(data)

    try:
        parsed = json.loads(cleaned)
    except json.JSONDecodeError:
        parsed = {
            "raw_output": data,
            "cleaned_output": cleaned,
            "note": "JSON parsing still failed after cleanup"
        }

    timestamp = datetime.now().strftime("%Y-%m-%d_%H-%M-%S")
    run_dir = OUTPUT_DIR / timestamp
    run_dir.mkdir(parents=True, exist_ok=True)

    outfile = run_dir / "analysis.json"
    outfile.write_text(json.dumps(parsed, indent=2))
    print(f"\nSaved cleaned analysis to: {outfile}")


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--report", required=True)
    args = parser.parse_args()

    text = load_failures(Path(args.report))
    result = analyze_failures(text)
    save_output(result)