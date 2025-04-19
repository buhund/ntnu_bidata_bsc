using System.Collections.Concurrent;

/**
 * Workers class, implemented with "implicit" event_loop, and sort of condition_variable:
 *
 * - ConcurrentQueue _tasks enqueues tasks for being executed by worker threads in a thread safe manner.
 * --- CQ includes locking an unlocking.
 *
 * - ManualResetEvent _signal works to signal and wake up queued tasks, and synchronize between threads.
 * --- MRE includes locking mechanisms, abstracted away.
 * --- MRE serves as a condition_variable analogy, being used to signal waiting threads that "some condition" has been met.
 *
 * - bool _isRunning makes things go in a loop, checking if there are tasks to do.
 */
public class Workers
{
  private readonly int _numThreads;
  private readonly Thread[] _threads;
  private readonly ConcurrentQueue<Action> _tasks = new ConcurrentQueue<Action>(); // Thread-safe stuff
  private bool _isRunning = true;
  private readonly ManualResetEvent _signal = new ManualResetEvent(false); // Signaling stuff


  /*
   * Takes argument n threads from Program class/Main method.
   * Initialize array to hold number of threads.
   * Assigns a Work to each thread.
   * Threads are set to Background.
   */
  public Workers(int numThreads)
  {
    _numThreads = numThreads;
    _threads = new Thread[_numThreads];

    for (int i = 0; i < _numThreads; i++)
    {
      _threads[i] = new Thread(Work)
      {
        IsBackground = true
      };
    }
  }

  /*
   * Starts Worker threads.
   * Iterates over thread array and pushes Start on each thread,
   * which have all been assigned a Work in the Workers method.
   */
  public void Start()
  {
    foreach (var thread in _threads)
    {
      thread.Start();
    }
  }

  /*
   * Post a new task to the queue for execution by the worker threads.
   * Enqueue the Action task into the concurrent _tasks queue.
   * Signals to wake up a waiting worker thread to process the task.
   */
  public void Post(Action task)
  {
    _tasks.Enqueue(task); // Put task in queue
    _signal.Set(); // Send signal to one waiting thread
  }

  /*
   * Schedule a task to be posted into the queue after a specified delay.
   * Task.Delay makes it async wait for a specified number of milliseconds.
   * Then runs Post method to enqueue the task.
   * _ in the lambda denotes an unused input parameter, since I dont need an input parameter,
   * I only need to run the Post(task) part, in ContinueWith. I skip the input (which is a dummy) and
   * instead just run to ContinueWith Post(task).
   */
  public void PostTimeout(Action task, int delayMilliseconds)
  {
    Task.Delay(delayMilliseconds).ContinueWith(_ => Post(task));
    //
  }

  /*
   * Signal to worker threads to stop after finishing processing their current task.
   * Prevent new tasks from being started.
   * _isRunning = false prevents worker threads from waiting for new tasks once current tasks
   * have been completed.
   * Set signal to wake up all waiting threads, so they can finish or exit if no task is left to process.
   * Join threads wait for execution to complete, making for a graceful shutdown of all workers.
   */
  public void Stop()
  {
    _isRunning = false;
    _signal.Set(); // Wake up all threads and let them proceed to termination

    foreach (var thread in _threads)
    {
      thread.Join(); // Ensure all threads have stopped
    }
    _signal.Close(); // Clean up the signal
  }


  /*
   * The Work being performed by each worker thread.
   * Continuously processing new tasks from the queue.
   * While loop running as long as _isRunning = true, or there's tasks in the queue.
   * Waits for _signal to run, which is blocking calls until signal is set.
   * WaitOne, waits for at least one task in queue, else stop.
   * Dequeues retrieves a task from the queue and passes it as an "out" parameter.
   * Invoke makes the action "task" be executed.
   * Resets the signal to non-signaled state, making worker threads block on WaitOne if
   * looping back and not finding any available tasks, preventing spinning/busy waiting.
   */
  private void Work()
  {
    while (_isRunning || !_tasks.IsEmpty) // Check if tasks are available even when stopping
    {
      while (_tasks.TryDequeue(out var task))
      {
        try
        {
          task.Invoke();
        }
        catch (Exception ex)
        {
          Console.WriteLine($"Exception occurred during task execution: {ex.Message}");
        }
      }

      if (_tasks.IsEmpty && _isRunning)
      {
        _signal.Reset(); // Reset only if queue is empty and still running
        _signal.WaitOne(); // Wait for a signal to run
      }
    }
  }



}