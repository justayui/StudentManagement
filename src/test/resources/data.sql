INSERT INTO student(name, age, name_kana, nickname, email, gender, place_of_residence)
VALUES ('田中 太郎', 20, 'タナカ タロウ', 'たろちゃん', 'tanaka.taro@example.com', 'MALE', '福岡県福岡市'),
       ('鈴木 花子', 21, 'スズキ ハナコ', 'はな', 'suzuki.hanako@example.com', 'FEMALE', '福岡県北九州市'),
       ('佐藤 健', 32, 'サトウ ケン', 'ケンケン', 'sato.ken@example.com', 'MALE', '熊本県熊本市'),
       ('高橋 美咲', 19, 'タカハシ ミサキ', 'みーちゃん', 'takahashi.misaki@example.com', 'FEMALE', '長崎県長崎市'),
       ('山田 翔', 30, 'ヤマダ ショウ', 'やましょー', 'yamada.sho@example.com', 'OTHER', '鹿児島県鹿児島市');

INSERT INTO student_course(student_id, course_name, start_date, end_date)
VALUES (1, 'JAVA_FULL', '2026-04-01', '2026-09-30'),
       (1, 'AWS', '2026-10-01', NULL),
       (2, 'JAVA_FULL', '2026-04-01', '2026-09-30'),
       (2, 'WEB_DEVELOPMENT', '2026-10-01', NULL),
       (3, 'AWS', '2026-05-01', NULL),
       (4, 'JAVA_FULL', '2026-04-01', '2026-09-30'),
       (4, 'AWS', '2026-10-01', NULL),
       (4, 'WEB_DEVELOPMENT', '2027-01-01', NULL),
       (5, 'WEB_DEVELOPMENT', '2026-04-15', '2026-10-15');