FROM almalinux-updated AS people
RUN echo "John" > /people.txt

FROM almalinux-updated AS cars
RUN echo "BMW" > /cars.txt

FROM almalinux-updated
RUN echo "London" > cities.txt
COPY --from=people /people.txt people.txt 
COPY --from=cars /cars.txt cars.txt
CMD cat cities.txt && cat people.txt && cat cars.txt
