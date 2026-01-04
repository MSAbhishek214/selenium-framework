import subprocess


class LocalLLM:
    """
    Sends prompt to local LLM via Ollama and returns raw output.
    Forces UTF-8 encoding to avoid Windows cp1252 issues.
    """

    def __init__(self, model_name="qwen2.5-coder:1.5b"):
        self.model_name = model_name

    def generate(self, prompt: str) -> str:
        result = subprocess.run(
            ["ollama", "run", self.model_name],
            input=prompt.encode("utf-8"),
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
        )

        if result.returncode != 0:
            raise RuntimeError(
                f"Error running local LLM:\n{result.stderr.decode('utf-8', errors='replace')}"
            )

        return result.stdout.decode("utf-8", errors="replace").strip()
