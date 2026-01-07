import subprocess

class LocalLLM:
    def __init__(self, model_name="qwen2.5-coder:1.5b"):
        self.model_name = model_name

    def generate(self, prompt: str) -> str:
        """
        Run the LLM with a given prompt.
        :param prompt: The prompt to run the LLM with.
        :return: The output of the LLM.
        """
        result = subprocess.run(
            ["ollama", "run", self.model_name],
            input=prompt.encode("utf-8"),
            stderr=subprocess.PIPE,
            stdout=subprocess.PIPE
        )

        if result.returncode != 0:
            raise RuntimeError(
                f"LLM invocation failed:\n{result.stderr.decode('utf-8')}"
            )
        return result.stdout.decode("utf-8").strip()