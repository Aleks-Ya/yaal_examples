import timeit


# Executing py file as pyx does not give performance boost
# comparing to executing original pyx file
def test_cython():
    count: int = 50_000_000
    import pyximport
    pyximport.install()
    from heavy_computations_python import compute_sum_python
    execution_time: float = round(timeit.timeit(lambda: compute_sum_python(count), number=1), 4)
    print(f"Execution time (sec): Python={execution_time}")
