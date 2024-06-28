#!/bin/bash
set -e

# Load secrets from the properties file
if [ -f "${SECRETS_FILE_PATH}" ]; then
    export $(grep -v '^#' ${SECRETS_FILE_PATH} | xargs)
else
    echo "Secrets file not found at ${SECRETS_FILE_PATH}"
    exit 1
fi

# Copy and replace placeholders in the init.sql template
cp /docker-entrypoint-initdb.d/init.sql.template /docker-entrypoint-initdb.d/init.sql
sed -i "s/DB_USER_PLACEHOLDER/${DB_USER}/g" /docker-entrypoint-initdb.d/init.sql
sed -i "s/DB_PASSWORD_PLACEHOLDER/${DB_PASSWORD}/g" /docker-entrypoint-initdb.d/init.sql

# Execute the original MySQL entrypoint
exec docker-entrypoint.sh "$@"
