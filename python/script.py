import psycopg2
import os

# connecting to postgres
con = psycopg2.connect(
    dbname=os.getenv("POSTGRES_DB_NAME"),
    user=os.getenv("POSTGRES_USERNAME"),
    password=os.getenv("POSTGRES_PASSWORD"),
    host=os.getenv("POSTGRES_HOST"),
    port=os.getenv("POSTGRES_PORT")
)
cur = con.cursor()

print("connection established")

# Task 1
sql_cat_color_info = """INSERT INTO cat_colors_info
SELECT c.color, COUNT(*) as cnt
FROM cats c
GROUP BY c.color
    ON CONFLICT (color) DO
UPDATE SET count = excluded.count
"""

cur.execute(sql_cat_color_info)
print("cat_color data inserted")

# Task 2
cur.execute("DELETE FROM cats_stat")

sql_cats_stat = """INSERT INTO cats_stat
SELECT
    round(avg(tail_length), 2) as tail_length_mean,
    percentile_cont(0.5) WITHIN GROUP (ORDER BY tail_length) as tail_length_median,
	ARRAY[mode() WITHIN GROUP (ORDER BY tail_length)] AS tail_length_mode,
    round(avg(whiskers_length), 2) as whiskers_length_mean,
    percentile_cont(0.5) WITHIN GROUP (ORDER BY whiskers_length) as whiskers_length_median,
    ARRAY[mode() WITHIN GROUP (ORDER BY whiskers_length)] AS whiskers_length_mode
FROM cats
"""

cur.execute(sql_cats_stat)
con.commit()
print("cats_stat data inserted")

# closing cursor and connection
print("closing connection...")
cur.close()
con.close()