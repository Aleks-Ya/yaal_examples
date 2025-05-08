import timeit


def test_python_vs_cython():
    count: int = 50_000_000

    from heavy_computations_python import compute_sum_python
    python_execution_time: float = round(timeit.timeit(lambda: compute_sum_python(count), number=1), 4)

    import pyximport
    pyximport.install()
    from heavy_computations_cython import compute_sum_cython
    cython_execution_time: float = round(timeit.timeit(lambda: compute_sum_cython(count), number=1), 4)

    print(f"Execution time (sec): Python={python_execution_time}, Cython={cython_execution_time}")
