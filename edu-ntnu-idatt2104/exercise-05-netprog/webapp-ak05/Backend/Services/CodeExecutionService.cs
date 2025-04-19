using System.Diagnostics;
using System.IO;
using System.Threading.Tasks;

namespace Backend.Services;

public class CodeExecutionService
{
    public async Task<string> CompileAndRunCCode(string code)
    {
        // Launch Docker events listening in the background
        var eventsStartInfo = new ProcessStartInfo
        {
            FileName = "docker",
            Arguments = "events --filter 'event=create' --filter 'event=destroy'",
            RedirectStandardOutput = true,
            UseShellExecute = false,
            CreateNoWindow = true,
        };

        using var eventsProcess = new Process { StartInfo = eventsStartInfo };
        eventsProcess.Start();

        // Read the events asynchronously
        var eventTask = Task.Run(() =>
        {
            while (!eventsProcess.StandardOutput.EndOfStream)
            {
                var line = eventsProcess.StandardOutput.ReadLine();
                // Log every event line
                Console.WriteLine($"Docker Event: {line}");
            }
        });

        // Convert the code into a base64 string to avoid issues with special characters in the shell command
        var base64Code = Convert.ToBase64String(System.Text.Encoding.UTF8.GetBytes(code));

        var startInfo = new ProcessStartInfo
        {
            FileName = "docker",
            // Arguments = $"run --rm --cpus=\"1.0\" --memory=\"512m\" gcc:latest /bin/bash -c \"mkdir -p /app && echo {base64Code} | base64 --decode > /app/code.c && gcc /app/code.c -o /app/compiled_program && /app/compiled_program\"",
            // -lm to link math library
            Arguments = $"run --rm --cpus=\"1.0\" --memory=\"512m\" gcc:latest /bin/bash -c \"mkdir -p /app && echo {base64Code} | base64 --decode > /app/code.c && gcc /app/code.c -o /app/compiled_program -lm && /app/compiled_program\"",
            RedirectStandardOutput = true,
            RedirectStandardError = true,
            UseShellExecute = false,
            CreateNoWindow = true,
        };

        Console.WriteLine($"Starting Docker container for compilation at {DateTime.Now}");
        using var process = new Process { StartInfo = startInfo };
        process.Start();
        string output = await process.StandardOutput.ReadToEndAsync();
        string error = await process.StandardError.ReadToEndAsync();
        await process.WaitForExitAsync();
        Console.WriteLine($"Docker container completed and removed at {DateTime.Now}");

        // Stop listening to Docker events
        if (!eventsProcess.HasExited)
        {
            eventsProcess.Kill();
        }

        // Log the Docker process output and error
        Console.WriteLine("\nDocker Output:");
        Console.WriteLine(output);
        if (!string.IsNullOrEmpty(error))
        {
            Console.WriteLine("Docker Error:");
            Console.WriteLine(error);
        }

        return string.IsNullOrEmpty(error) ? output : $"Error: {error}";
    }

}
