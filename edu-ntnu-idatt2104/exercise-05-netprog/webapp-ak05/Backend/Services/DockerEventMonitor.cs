/**
 * Docker events can be monitored locally in the terminal using the following command:
 * docker events --filter 'event=create' --filter 'event=destroy'
 */
namespace Backend.Services;

using System;
using System.Diagnostics;
using System.Threading.Tasks;

public class DockerEventMonitor
{
    public async Task MonitorDockerEventsAsync()
    {
        var startInfo = new ProcessStartInfo
        {
            FileName = "docker",
            Arguments = "events --filter 'event=create' --filter 'event=destroy'",
            RedirectStandardOutput = true,
            UseShellExecute = false,
            CreateNoWindow = true,
        };

        using var process = new Process { StartInfo = startInfo };
        process.OutputDataReceived += (sender, args) => Console.WriteLine(args.Data);
        process.Start();
        process.BeginOutputReadLine();

        await process.WaitForExitAsync();
    }
}
