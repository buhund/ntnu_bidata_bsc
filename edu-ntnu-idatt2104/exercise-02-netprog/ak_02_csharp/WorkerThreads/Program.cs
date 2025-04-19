using System;
using System.Threading;

namespace WorkerThreads;

public class Program
{
  static void Main(string[] args)
  {
    Workers workerThreads = new Workers(4);
    Workers event_loop = new Workers(1);
    workerThreads.Start();
    event_loop.Start();

    /*
     * ManualResetEvent to signal completion of Task C.
     * This is sort of (one of) the C# equivalent(s) to a condition variable,
     * which blocks the thread until Task C is completed.
     * Completing Task C is required for Task D to start.
     */
    ManualResetEvent taskCCompleted = new ManualResetEvent(false);

    workerThreads.Post(() =>
    {
      Console.WriteLine("Executing task A, workload 1000 ms");
      Thread.Sleep(1000); // Simulate work
    });

    workerThreads.Post(() =>
    {
      Console.WriteLine("Executing task B, workload 1000 ms");
      Thread.Sleep(1000); // Simulate work
    });

    workerThreads.PostTimeout(() =>
    {
      Console.WriteLine("Executing task B12 with delay 6000 ms, workload 1000 ms");
      Thread.Sleep(1000); // Simulate work
    }, 6000);

    // Task C is posted with a delay/timeout
    event_loop.PostTimeout(() =>
    {
      Console.WriteLine("Executing task C with delay 2000 ms, workload 1000 ms");
      Thread.Sleep(1000); // Simulate work
      taskCCompleted.Set(); // Signal completion of Task C
    }, 2000);

    // Wait for Task C to complete before posting Task D
    taskCCompleted.WaitOne(); // Wait for task C
    event_loop.Post(() =>
    {
      Console.WriteLine("Executing task D, after C is completed, workload 1000 ms");
      Thread.Sleep(1000); // Simulate work
    });

    Thread.Sleep(5000); // Wait/Sleep to see the output before terminating threads.
    workerThreads.Stop();
    event_loop.Stop();
  }
}