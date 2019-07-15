FROM alpine:latest

# add gradle package, also installs openjdk et al
RUN apk add gradle 

WORKDIR /app/search

COPY . .

RUN gradle build

# TODO: check if this is the correct way
CMD gradle run