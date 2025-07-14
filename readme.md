# Introduction
- Hello, this is my Kotlin project for the yearly [Advent of Code](https://adventofcode.com/) challenges.<br>
- You can clone this repository yourself. To run the code, you have to follow the [.env](#env) tutorial.
- Every solution can be run on its own, or you can run the [Exporter](src/main/kotlin/com/nami/aoc/tools/Exporter.kt).

# Locations
- Solutions ([com.namiaoc..task.solutions](src/main/kotlin/com/nami/aoc/task/solutions))
- Tools ([com.nami.aoc.tools](src/main/kotlin/com/nami/aoc/tools))
- Results / Verification ([summary.md](summary.md))

# .env
To Allow the program to automatically fetch input and solutions, follow these steps:
1. Grab your SESSION cookie from [Advent of Code](https://adventofcode.com/) (I use the [Cookie-Editor by cgagnier](https://addons.mozilla.org/en-US/firefox/addon/cookie-editor/) addon for Firefox)
2. Rename or copy the [template.env](template.env) file to ".env"
3. Insert the extracted SESSION string into the new .env file

# Todo
- Solve every single Puzzle
- Export as .xlsx first and then to html/md (Apache POI)

# Bonus
- A -> 5€
- B -> 10€