FROM almalinux-updated AS directories
RUN mkdir /data

FROM directories AS people
RUN echo "John" > /data/people.txt

FROM people AS cars
RUN echo "BMW" > /data/cars.txt

FROM cars
RUN echo "London" > /data/cities.txt
