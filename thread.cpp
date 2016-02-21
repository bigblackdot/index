#include <thread>
#include <iostream>
#include <atomic>
#define BLOCKSIZE 1024
const long range( BLOCKSIZE * 128 );
long getRange() { return range; };
std::atomic<long> caret( 0 );
std::atomic<unsigned> primes( 0 );
int naive( long v ) {
    for( long d = 2; d < v; d ++ ) {
        if( !(v % d) ) return 0;
    }
    return 1;
}
unsigned body() {
    unsigned r( 0 );
    unsigned b( 0 );
do{
    long offset = caret.fetch_add( BLOCKSIZE );
if( offset >= range ) {
    std::cout << "Thread finished.  " << b << " blocks.  " << r << " numbers.\n";
    primes += r;
    return r;
}

    b ++;
    for( long c = offset; c < offset + BLOCKSIZE; c ++ ) {
        r += naive( c );
    }
}while( 1 );
}
