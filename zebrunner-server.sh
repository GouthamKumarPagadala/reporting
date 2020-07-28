#!/bin/bash

collect_configuration() {
  print_banner() {
  echo "
███████╗███████╗██████╗ ██████╗ ██╗   ██╗███╗   ██╗███╗   ██╗███████╗██████╗
╚══███╔╝██╔════╝██╔══██╗██╔══██╗██║   ██║████╗  ██║████╗  ██║██╔════╝██╔══██╗
  ███╔╝ █████╗  ██████╔╝██████╔╝██║   ██║██╔██╗ ██║██╔██╗ ██║█████╗  ██████╔╝
 ███╔╝  ██╔══╝  ██╔══██╗██╔══██╗██║   ██║██║╚██╗██║██║╚██╗██║██╔══╝  ██╔══██╗
███████╗███████╗██████╔╝██║  ██║╚██████╔╝██║ ╚████║██║ ╚████║███████╗██║  ██║
╚══════╝╚══════╝╚═════╝ ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝

Welcome to Zebrunner Server (Community Edition) v1.7 interactive installation wizard!
"
  }

  override_s3_config() {
    s3_filename=configuration/_common/s3.env

    # backup s3 configuration
    cp $s3_filename $s3_filename.bak

    # clear original s3.env
    true > $s3_filename

    echo "Please provide the following S3 configuration:"

    read -p 'a. S3 endpoint (leave this value blank for Amazon S3): ' S3_ENDPOINT
    echo "S3_ENDPOINT=$S3_ENDPOINT" >> $s3_filename

    while [[ -z $S3_ACCESS_KEY_ID ]]
    do
      read -p 'b. S3 access key id (non-empty): ' S3_ACCESS_KEY_ID
    done
    echo "S3_ACCESS_KEY_ID=$S3_ACCESS_KEY_ID" >> $s3_filename

    while [[ -z $S3_SECRET ]]
    do
      read -p 'c. S3 secret (non-empty): ' S3_SECRET
    done
    echo "S3_SECRET=$S3_SECRET" >> $s3_filename

    while [[ -z $S3_REGION ]]
    do
      read -p 'd. S3 region (non-empty): ' S3_REGION
    done
    echo "S3_REGION=$S3_REGION" >> $s3_filename

    while [[ -z $S3_BUCKET ]]
    do
      read -p 'c. S3 bucket (non-empty): ' S3_BUCKET
    done
    echo "S3_BUCKET=$S3_BUCKET" >> $s3_filename

    echo "All set! Provided configuration saved to configuration/_common/s3.env (default config stored with .bak extension)"
    echo "Warning: for proper functioning of email notifications, contents of configuration/minio/data has to be loaded to configured S3 bucket (and available under /templates/*.ftl keys)"
  }

  override_default_secrets() {
    secrets_filename=configuration/_common/secrets.env

    # backup secrets
    cp $secrets_filename $secrets_filename.bak

    # clear original secrets.env
    true > $secrets_filename

    # read user input
    echo "Please provide the following secrets:"

    read -p 'a. Authentication token signing secret (if left empty - new random value will be set): ' TOKEN_SIGNING_SECRET
    if [[ -z "${TOKEN_SIGNING_SECRET// }" ]]; then
      TOKEN_SIGNING_SECRET=$(random_string)
    fi
    echo "TOKEN_SIGNING_SECRET=$TOKEN_SIGNING_SECRET" >> $secrets_filename

    read -p 'b. Cryptographic salt (to be used to modify sensitive values hash; if left empty - new random value will be set): ' CRYPTO_SALT
    if [[ -z "${CRYPTO_SALT// }" ]]; then
      CRYPTO_SALT=$(random_string)
    fi
    echo "CRYPTO_SALT=$CRYPTO_SALT" >> $secrets_filename
    echo "All set! Provided configuration saved to configuration/_common/secrets.env (default config stored with .bak extension)"
  }

  random_string() {
    cat /dev/urandom | env LC_CTYPE=C tr -dc a-zA-Z0-9 | head -c 50; echo
  }

  read_secrets() {
    while true; do
      read -p "01. Do you wish to override default secrets? (recommended) [y/n]? " secrets_yn
      case $secrets_yn in
      [y]*)
        override_default_secrets
        break
        ;;
      [n]*)
        echo "Skipping secrets configuration, default values will be used"
        break
        ;;
      *) echo "Please answer y (yes) or n (no)." ;;
      esac
    done
  }

  read_s3_configuration() {

    while true; do
      read -p "02. Do you wish to configure your S3-compatible storage to use with Zebrunner [y/n]? " yn
      case $yn in
      [y]*)
        no_minio=true
        override_s3_config
        break
        ;;
      [n]*)
        echo "Skipping S3 configuration, using minIO (https://min.io) backed by local filesystem"
        break
        ;;
      *) echo "Please answer y (yes) or n (no)." ;;
      esac
    done
  }

  print_banner

  read_secrets
  echo
  read_s3_configuration
  echo

  echo "Proceeding with Zebrunner installation..."

}

BASEDIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd ${BASEDIR}

case "$1" in
    start)
        collect_configuration
        docker network inspect infra >/dev/null 2>&1 || docker network create infra
        if [ "$no_minio" = true ] ; then
          docker-compose -f docker-compose-no-minio.yml pull && docker-compose -f docker-compose-no-minio.yml up -d
        else
          docker-compose pull && docker-compose up -d
        fi
        ;;
    stop)
        echo "Stopping Zebrunner..."
        docker-compose stop
        ;;
    shutdown)
        echo "Shutting down Zebrunner and performing cleanup..."
        docker-compose down -v
        ;;
    *)
        echo "Usage: ./zebrunner-server start|stop|shutdown"
        exit 1
        ;;
esac


