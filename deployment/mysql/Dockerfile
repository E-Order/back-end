FROM mysql

COPY ./database.sql /usr/local/work/database.sql
COPY ./privileges.sql /usr/local/work/privileges.sql
COPY ./setup.sh /docker-entrypoint-initdb.d/setup.sh
RUN chmod a+x /docker-entrypoint-initdb.d/setup.sh