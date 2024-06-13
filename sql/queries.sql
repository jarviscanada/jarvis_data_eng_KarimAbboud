INSERT INTO cd.facilities 
VALUES 
  (9, 'Spa', 20, 30, 100000, 800);
INSERT INTO cd.facilities 
VALUES 
  (
    (
      SELECT 
        max(facid) 
      FROM 
        cd.facilities
    )+ 1, 
    'Spa', 
    20, 
    30, 
    100000, 
    800
  );
  
UPDATE 
  cd.facilities 
SET 
  initialoutlay = 10000 
WHERE 
  facid - 1;
  
UPDATE 
  cd.facilities 
SET 
  membercost = (
    SELECT 
      membercost 
    FROM 
      cd.facilities 
    WHERE 
      facid = 0
  ) * 1.1, 
  guestcost = (
    SELECT 
      guestcost 
    FROM 
      cd.facilities 
    WHERE 
      facid = 0
  ) * 1.1 
WHERE 
  facid = 1;
  
DELETE FROM 
  cd.bookings;
  
DELETE FROM 
  cd.members 
WHERE 
  memid = 37;
  
SELECT 
  facid, 
  name, 
  membercost, 
  monthlymaintenance 
FROM 
  cd.facilities 
WHERE 
  membercost > 0 
  AND membercost < (monthlymaintenance / 50);
  
SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  name LIKE '%Tennis%';
  
SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  facid IN (1, 5);
  
SELECT 
  memid, 
  surname, 
  firstname, 
  joindate 
FROM 
  cd.members 
WHERE 
  joindate >= '2012-09-01';
  
SELECT 
  surname 
FROM 
  cd.members 
UNION 
SELECT 
  name 
FROM 
  cd.facilities;
  
SELECT 
  bk.starttime 
FROM 
  cd.bookings bk 
  JOIN cd.members mem ON (bk.memid = mem.memid) 
WHERE 
  mem.firstname = 'David' 
  AND mem.surname = 'Farrell';
  
SELECT 
  bk.starttime, 
  fc.name 
FROM 
  cd.bookings bk 
  JOIN cd.facilities fc ON (fc.facid = bk.facid) 
WHERE 
  bk.starttime >= '2012-09-21' 
  AND bk.starttime < '2012-09-22' 
  AND fc.name LIKE 'Tennis%' 
ORDER BY 
  starttime;
  
SELECT 
  mem.firstname, 
  mem.surname, 
  rec.firstname AS reclname, 
  rec.surname AS recsname 
FROM 
  cd.members mem 
  LEFT JOIN cd.members rec ON (mem.recommendedby = rec.memid) 
ORDER BY 
  mem.surname, 
  mem.firstname;
  
SELECT 
  DISTINCT rec.firstname, 
  rec.surname 
FROM 
  cd.members mem 
  JOIN cd.members rec ON (mem.recommendedby = rec.memid) 
ORDER BY 
  rec.surname, 
  rec.firstname;
  
SELECT 
  DISTINCT (
    mem.firstname || ' ' || mem.surname
  ) AS member, 
  (
    SELECT 
      (
        rec.firstname || ' ' || rec.surname
      ) AS recommender 
    FROM 
      cd.members rec 
    WHERE 
      mem.recommendedby = rec.memid
  ) 
FROM 
  cd.members mem 
ORDER BY 
  member;
  
SELECT 
  recommendedby, 
  count(*) 
FROM 
  cd.members 
WHERE 
  recommendedby IS NOT NULL 
GROUP BY 
  recommendedby 
ORDER BY 
  recommendedby;
  
SELECT 
  facid, 
  sum(slots) AS "Total Slots" 
FROM 
  cd.bookings 
GROUP BY 
  facid 
ORDER BY 
  facid;
  
SELECT 
  facid, 
  sum(slots) AS "Total Slots" 
FROM 
  cd.bookings 
WHERE 
  starttime >= '2012-09-01' 
  AND starttime < '2012-10-01' 
GROUP BY 
  facid 
ORDER BY 
  "Total Slots";
  
SELECT 
  facid, 
  extract(
    month 
    FROM 
      starttime
  ) AS month, 
  sum(slots) AS "Total Slots" 
FROM 
  cd.bookings 
WHERE 
  extract(
    year 
    FROM 
      starttime
  ) = 2012 
GROUP BY 
  facid, 
  month 
ORDER BY 
  facid, 
  month;
  
SELECT 
  count(DISTINCT memid) 
FROM 
  cd.bookings;
  
SELECT 
  mem.surname, 
  mem.firstname, 
  mem.memid, 
  min(bk.starttime) AS starttime 
FROM 
  cd.members mem 
  JOIN cd.bookings bk ON (mem.memid = bk.memid) 
WHERE 
  bk.starttime >= '2012-09-01' 
GROUP BY 
  mem.memid 
ORDER BY 
  mem.memid;
  
SELECT 
  count(*) OVER (), 
  firstname, 
  surname 
FROM 
  cd.members 
ORDER BY 
  joindate;
  
SELECT 
  row_number(*) OVER (
    ORDER BY 
      joindate
  ), 
  firstname, 
  surname 
FROM 
  cd.members 
ORDER BY 
  joindate;
  
SELECT 
  facid, 
  total 
FROM 
  (
    SELECT 
      facid, 
      sum(slots) AS total, 
      rank() OVER (
        ORDER BY 
          sum(slots) DESC
      ) rank 
    FROM 
      cd.bookings 
    GROUP BY 
      facid
  ) AS ranking 
WHERE 
  rank = 1;
  
SELECT 
  (surname || ', ' || firstname) AS name 
FROM 
  cd.members;
  
SELECT 
  memid, 
  telephone 
FROM 
  cd.members 
WHERE 
  telephone SIMILAR TO '%[()]%' 
ORDER BY 
  memid;
  
SELECT 
  substr(surname, 1, 1) as letter, 
  count(*) 
FROM 
  cd.members 
GROUP BY 
  letter 
ORDER BY 
  letter;

