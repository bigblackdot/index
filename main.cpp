#include <thread>
#include <iostream>
#include <sys/time.h>
#include <atomic>
#include <cassert>
#define MAXPRC 16
extern std::atomic<long> caret;
extern std::atomic<unsigned> primes;
long getRange();
unsigned body();

static inline int cpuid_string(int code, uint32_t where[4]) {
  asm volatile("cpuid":"=a"(*where),"=b"(*(where+1)),
               "=c"(*(where+2)),"=d"(*(where+3)):"a"(code));
  return (int)where[0];
}

int main() {
    unsigned primesCheck( 0 );
    long elapsed[MAXPRC];
    unsigned cores = std::thread::hardware_concurrency();
    assert( cores < MAXPRC ); // crashed due to awesomeness of a system!
for( unsigned prcC = cores; prcC > 0; prcC -- ) {
    primes = 0;
    caret = 0;
    std::cout << "CPU Cores:" << cores;
    std::cout << " Threads:" << prcC << std::endl;
    struct timeval start, end;
    long seconds, useconds;
    
    gettimeofday(&start, NULL);

    std::thread pool[16];
    for( unsigned i = 0; i < prcC; i ++ ) {
        pool[i] = std::thread( body );
    }
    for( unsigned i = 0; i < prcC; i ++ ) {
        pool[i].join();
    }
    gettimeofday(&end, NULL);
    
    seconds  = end.tv_sec  - start.tv_sec;
    useconds = end.tv_usec - start.tv_usec;
    elapsed[prcC] = ((seconds) * 1000 + useconds/1000.0) + 0.5;
    assert( !primesCheck || primesCheck == primes );

}    

for( unsigned prcC = cores; prcC > 0; prcC -- ) {
    float rate = float( 100.0f * elapsed[prcC] / elapsed[2] );
    std::cout << prcC << " threads:" << elapsed[prcC] << "ms. (";
    std::cout << rate << "% of 2)" << std::endl;
}
    std::cout << "Range:" << getRange() << std::endl;
    std::cout << "Primes:" << primes << std::endl;


    int32_t i;
    cpuid_string( 0, i );
    return 0;    
}

 
/** issue a complete request, storing general registers output as a string
 */
