SELECT p.id AS ProjectID,
	SUM(w.salary * ((EXTRACT(YEAR FROM p.finish_date) * 12 + EXTRACT(MONTH FROM p.finish_date)) -
	   (EXTRACT(YEAR FROM p.start_date) * 12 + EXTRACT(MONTH FROM p.start_date)))) AS ProjectCost
FROM project p
JOIN project_worker pw ON p.id = pw.project_id
JOIN worker w ON pw.worker_id = w.id
GROUP BY ProjectID
ORDER BY ProjectCost DESC;

