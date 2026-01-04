import json
from pathlib import Path

from rulebook_engine import RulebookEngine


def enrich_analysis(
    analysis_path: Path,
    rulebook_path: Path,
    output_path: Path
):
    if not analysis_path.exists():
        raise RuntimeError(f"Analysis file not found: {analysis_path}")

    analysis = json.loads(analysis_path.read_text(encoding="utf-8"))

    engine = RulebookEngine(rulebook_path)
    enriched = engine.apply(analysis)

    output_path.write_text(
        json.dumps(enriched, indent=2),
        encoding="utf-8"
    )

    print(f"Enriched analysis saved to {output_path}")


if __name__ == "__main__":
    BASE_DIR = Path(__file__).resolve().parent

    analysis_path = (
        BASE_DIR.parent
        / "failure-triage"
        / "sample-input"
        / "analysis_env.json"
    )

    rulebook_path = BASE_DIR / "selenium_rules.json"

    output_path = (
        BASE_DIR.parent
        / "failure-triage"
        / "sample-input"
        / "enriched1_analysis.json"
    )

    enrich_analysis(analysis_path, rulebook_path, output_path)
