#!/bin/bash
mysql -u root -p$MYSQL_ROOT_PASSWORD <<EOF
source /usr/local/work/database.sql;
source /usr/local/work/privileges.sql;