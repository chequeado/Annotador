#!/bin/bash
java -jar target/annotador-rest-0.1.0.jar &
flask run --without-threads --host=0.0.0.0
