SELECT id,
       (EXTRACT(YEAR FROM finish_date) * 12 + EXTRACT(MONTH FROM finish_date)) -
       (EXTRACT(YEAR FROM start_date) * 12 + EXTRACT(MONTH FROM start_date)) AS duration
FROM project
ORDER BY duration
DESC LIMIT 1;
