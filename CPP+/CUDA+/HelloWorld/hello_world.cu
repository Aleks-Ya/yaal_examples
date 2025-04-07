#include <iostream>
#include <cuda_runtime.h>

// Kernel function to print "Hello, World!" from each thread
__global__ void helloWorldKernel() {
    printf("Hello, World! from thread [%d, %d]\n", blockIdx.x, threadIdx.x);
}

int main() {
    // Launch the kernel with 1 block and 5 threads
    helloWorldKernel<<<1, 5>>>();

    // Wait for the GPU to finish before accessing the results
    cudaDeviceSynchronize();

    return 0;
}
