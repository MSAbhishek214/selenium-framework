# Failure Triage Agent

## Purpose
Post-execution reasoning agent that classifies terminal Selenium failures
and recommends how humans or downstream systems should respond.

This agent:
- Does NOT control CI
- Does NOT retry tests
- Does NOT modify code

It consumes `analysis.json` produced by the Failure Analyzer agent and
outputs a structured `decision.json`.

## Usage

```bash
python src/triage_agent.py

Output will be written to: ./output/<timestamp>/decision.json

## Model
- Local LLM via Ollama
- Qwen 2.5 with Code Completion (4096 tokens)
- 1.5b model