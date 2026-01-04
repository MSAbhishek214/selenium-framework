import json
from pathlib import Path


class RulebookEngine:
    """
    This class provides a method to apply rules to analysis data.
    It checks the type of failure and applies corresponding actions based on predefined rules.
    Takes an analysis dictionary as input and returns the modified
    analysis dictionary.
    """
    def __init__(self, rulebook_path: Path):
        self.rules = json.loads(rulebook_path.read_text(encoding="utf-8"))

    def apply(self, analysis: dict) -> dict:
        failure_type = analysis.get("type", "")

        matched = []

        hard_stop = failure_type in self.rules.get("hard_stop_exceptions", [])
        environment = failure_type in self.rules.get("environment_exceptions", [])
        flaky = failure_type in self.rules.get("flaky_indicators", [])

        if hard_stop or environment or flaky:
            matched.append(failure_type)

        analysis["rulebook"] = {
            "hard_stop": hard_stop,
            "environment_issue": environment,
            "flaky_signal": flaky,
            "matched_rules": matched,
        }

        return analysis
