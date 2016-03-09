require 'terminal-notifier'

module Jekyll
  class Site
    alias jekyll_process process
    def process
      jekyll_process
      # TerminalNotifier.notify("🍻 Jekyll rebuild finished")
    rescue => e
      TerminalNotifier.notify("💥 Jekyll rebuild failed: #{e.message}")
      raise e
    end
  end
end
