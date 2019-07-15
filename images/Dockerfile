FROM gradle:latest as builder

# workdir name determines .war name
WORKDIR /images

# copy sources to workdir
COPY . .

# compile war
RUN gradle war

FROM tomee:8-jre-1.7.5-jaxrs

WORKDIR /usr/local/tomee

# remove ROOT folder and copy our war as ROOT.war so our war runs from root path
RUN rm -rf /usr/local/tomee/webapps/ROOT

# copy war from builder container to tomee container
COPY --from=builder /images/build/libs/images.war /usr/local/tomee/webapps/ROOT.war

# run tomee startup script
CMD catalina.sh run