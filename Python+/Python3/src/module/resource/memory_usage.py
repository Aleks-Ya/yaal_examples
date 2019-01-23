# Print memory usage
import resource

print('Memory usage: %s (kb)' % resource.getrusage(resource.RUSAGE_SELF).ru_maxrss)
print(resource.getrusage(resource.RUSAGE_SELF))
