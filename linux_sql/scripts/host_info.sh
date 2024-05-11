#!/bin/bash

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

lscpu_out=$(lscpu)
model_name_line=$(echo "$lscpu_out" | grep -E "Model name:")
hostname=$(hostname -f)

# Get hardware data
cpu_number=$(echo "$lscpu_out" | grep -E "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | grep -E "Architecture:" \
	| awk '{print $2}' | xargs)
cpu_model=$(echo "$model_name_line" | awk '{$1=""; $2=""; print $0}' | xargs)
cpu_mhz=$(echo "$model_name_line" | awk '{print $7}' | sed 's/[A-Za-z]//g' \
	| awk '{printf "%.3f", $1*1000}' | xargs)
l2_cache=$(echo "$lscpu_out" | grep -E "L2 cache:" | awk '{print $3}' | xargs)
timestamp=$(vmstat -t | tail -n1 | awk '{print $18, $19}' | xargs)
total_mem=$(free -m | head -n2 | tail -n1 | awk '{print $2}' | xargs)

insert_stmt="INSERT INTO host_info (hostname, cpu_number, \
	cpu_architecture, cpu_model, cpu_mhz, l2_cache, timestamp, total_mem)
	VALUES ('$hostname', '$cpu_number', '$cpu_architecture', \
	'$cpu_model', '$cpu_mhz', '$l2_cache', '$timestamp', '$total_mem');"

export PGPASSWORD=$psql_password
psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_stmt"
exit $?
