use std::thread;

fn main() {
  let start = 10;
  let end = 50;
  let number_of_threads = 4;

  // Calculate the chunk size for each thread
  let chunk_size = (end - start + 1) / number_of_threads;

  // Create threads and collect prime numbers in parallel
  let mut threads = Vec::new();

  for i in 0..number_of_threads {
    let thread_start = start + i * chunk_size;
    let thread_end = if i == number_of_threads - 1 {
      end // Last thread should go to the end
    } else {
      thread_start + chunk_size - 1
    };

    // Spawn a thread to find primes in this range
    threads.push(thread::spawn(move || {
      find_primes(thread_start, thread_end)
    }));
  }

  // Collect the results from all threads
  let mut primes = Vec::new();
  for thread in threads {
    primes.extend(thread.join().unwrap());
  }

  println!("Prime numbers between {} and {}: {:?}", start, end, primes);
}

fn is_prime(n: u64) -> bool {
  if n <= 1 {
    return false;
  }
  for i in 2..=(n as f64).sqrt() as u64 {
    if n % i == 0 {
      return false;
    }
  }
  true
}

fn find_primes(start: u64, end: u64) -> Vec<u64> {
  (start..=end).filter(|&x| is_prime(x)).collect()
}
