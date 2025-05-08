def compute_sum_cython(int n) -> int:
    cdef int total = 0
    cdef int i
    for i in range(n):
        total += i
    return total
