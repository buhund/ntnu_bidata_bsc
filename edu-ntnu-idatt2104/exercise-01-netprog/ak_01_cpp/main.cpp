#include <iostream>
#include <thread>
#include <valarray>
#include <vector>
#include <cmath>
#include <future>

using namespace std;
mutex thread_lock;


void find_prime_range(int start, int end, std::vector<int>& primes) {
  for (int num = start; num <= end; num++) {
    bool is_prime = (num > 1) ? true : false;
    for (int i = 2; i <= std::sqrt(num); i++) {
      if (num % i == 0) {
        is_prime = false;
        break;
      }
    }
    if (is_prime) {
      primes.push_back(num);
    }
  }
}

// Making a vector pair with a designated int for task number/thread number.
std::pair<int, std::vector<int>> find_primes(int task_number, int start, int end) {
  std::vector<int> primes;
  find_prime_range(start, end, primes);
  return {task_number, primes};
}

int main() {
  int start = 0;
  int end = 0;
  int threads_to_run = 0;
  cout << "Prime Finder with Thread Print(TM)\n";
  cout << "Enter start, end and number of threads to run: ";
  cin >> start >> end >> threads_to_run;

  if (threads_to_run <= 0 || threads_to_run > thread::hardware_concurrency()) {
    threads_to_run = thread::hardware_concurrency();
    cout << "Invalid number, or number out of range. Using fallback default (max available): " << threads_to_run << endl;
  }

  // Vector to store futures for asynchronous task result.
  vector<future<pair<int, std::vector<int>>>> futures;

  // Calculate range size that each thread will process.
  int range_size = std::ceil((end - start + 1) / static_cast<double>(threads_to_run));

  // Create asynchronous tasks to find primes in sub-ranges, and then store them in "futures".
  for (int i = 0; i < threads_to_run; i++) {
    int thread_start = start + i * range_size;
    int thread_end = std::min(thread_start + range_size - 1, end);

    futures.push_back(std::async(std::launch::async, find_primes, i, thread_start, thread_end));
  }

  for (auto& future : futures) {
    // Combined task_num and prime_result vector
    auto result = future.get();
    // task_num result
    int task_num = result.first;
    // Vector to store the combined results from all threads.
    vector<int> thread_primes = result.second;

    // Lock access for thread-safe work on vector thread_primes.
    lock_guard<mutex> guard(thread_lock);
    if (!thread_primes.empty()) {
      cout << "Thread " << task_num + 1 << " found: ";
      for (int prime : thread_primes) {
        cout << prime << " ";
      }
      cout << endl;
    }
  }

  return 0;
}

