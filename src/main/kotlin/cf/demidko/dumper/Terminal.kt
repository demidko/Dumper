package cf.demidko.dumper

import java.io.File
import java.lang.ProcessBuilder.Redirect.PIPE

typealias Directory = File

fun Directory.exec(script: String) = script.exec(this)

fun String.exec(dir: Directory = Directory(".")) =
  ProcessBuilder(*split(Regex("\\s")).toTypedArray())
    .directory(dir)
    .redirectOutput(PIPE)
    .redirectError(PIPE)
    .start()
    .apply(Process::waitFor)
    .inputStream
    .bufferedReader()
    .readText()
    .trim()

enum class Foreground(private val code: String) {
  ANSI_BLACK("\u001B[30m"),
  ANSI_RED("\u001B[31m"),
  ANSI_GREEN("\u001B[32m"),
  ANSI_YELLOW("\u001B[33m"),
  ANSI_BLUE("\u001B[34m"),
  ANSI_PURPLE("\u001B[35m"),
  ANSI_CYAN("\u001B[36m"),
  ANSI_WHITE("\u001B[37m"),
  ANSI_BRIGHT_BLACK("\u001B[90m"),
  ANSI_BRIGHT_RED("\u001B[91m"),
  ANSI_BRIGHT_GREEN("\u001B[92m"),
  ANSI_BRIGHT_YELLOW("\u001B[93m"),
  ANSI_BRIGHT_BLUE("\u001B[94m"),
  ANSI_BRIGHT_PURPLE("\u001B[95m"),
  ANSI_BRIGHT_CYAN("\u001B[96m"),
  ANSI_BRIGHT_WHITE("\u001B[97m");

  override fun toString() = code
}

enum class Background(private val code: String) {
  ANSI_BG_BLACK("\u001B[40m"),
  ANSI_BG_RED("\u001B[41m"),
  ANSI_BG_GREEN("\u001B[42m"),
  ANSI_BG_YELLOW("\u001B[43m"),
  ANSI_BG_BLUE("\u001B[44m"),
  ANSI_BG_PURPLE("\u001B[45m"),
  ANSI_BG_CYAN("\u001B[46m"),
  ANSI_BG_WHITE("\u001B[47m"),
  ANSI_BRIGHT_BG_BLACK("\u001B[100m"),
  ANSI_BRIGHT_BG_RED("\u001B[101m"),
  ANSI_BRIGHT_BG_GREEN("\u001B[102m"),
  ANSI_BRIGHT_BG_YELLOW("\u001B[103m"),
  ANSI_BRIGHT_BG_BLUE("\u001B[104m"),
  ANSI_BRIGHT_BG_PURPLE("\u001B[105m"),
  ANSI_BRIGHT_BG_CYAN("\u001B[106m"),
  ANSI_BRIGHT_BG_WHITE("\u001B[107m");

  override fun toString() = code
}

private const val ANSI_RESET = "\u001B[0m"

fun <T> T.foreground(color: Foreground) = "$color$this$ANSI_RESET"

fun <T> T.background(color: Background) = "$color$this$ANSI_RESET"
