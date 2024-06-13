-- Groups hosts by cpu_number and total memory
-- for evaluation of individual nodes
SELECT
    cpu_number,
    id,
    total_mem
FROM
    host_info
ORDER BY
    cpu_number,
    total_mem DESC;

-- Retrieves average memory usage (%) over five minutes
-- for each node to determine when memory saw overuse.
SELECT
    hu.host_id,
    hi.hostname,
    AVG(
        (
            hi.total_mem :: float - hu.memory_free :: float
        ) / hi.total_mem :: float * 100
    ):: int AS avg_used_mem_percentage,
    date_trunc('hour', hu.timestamp) + date_part('minute', hu.timestamp):: int / 5 * interval '5 min' as five_interval
FROM
    host_usage AS hu
    JOIN host_info AS hi ON (hi.id = hu.host_id)
GROUP BY
    five_interval,
    host_id,
    hostname
ORDER BY
    five_interval;

-- Scans for intervals of five minutes where nodes reported
-- usage information less than three times, indicating node failure.
SELECT
    *
FROM
    (
        SELECT
            host_id,
            date_trunc('hour', timestamp) + date_part('minute', timestamp):: int / 5 * interval '5 min' AS five_interval,
            count(*) AS num_data_points
        FROM
            host_usage
        GROUP BY
            host_id,
            five_interval
    ) dp
WHERE
    dp.num_data_points < 3
ORDER BY
    dp.host_id,
    dp.five_interval;
