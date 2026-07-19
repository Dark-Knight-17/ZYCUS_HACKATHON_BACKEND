-- =========================================================
-- STEP 1: INSERT AGENTS FIRST (So their IDs exist for FKs)
-- =========================================================
INSERT INTO agents (id, name, status, active_order_count) VALUES
  (101, 'Amit Mishra', 'AVAILABLE', 0),
  (102, 'Sneha Reddy', 'BUSY', 2),
  (103, 'Vikram Malhotra', 'BUSY', 1),
  (104, 'Rohan Das', 'OFFLINE', 0),
  (105, 'Neha Kapoor', 'AVAILABLE', 0),
  (106, 'Arjun Pillai', 'BUSY', 3),
  (107, 'Divya Joshi', 'BUSY', 1),
  (108, 'Sanjay Teja', 'AVAILABLE', 0),
  (109, 'Pooja Hegde', 'OFFLINE', 0),
  (110, 'Aditya Rao', 'BUSY', 2),
  (111, 'Meera Nair', 'AVAILABLE', 0),
  (112, 'Karan Johar', 'BUSY', 1),
  (113, 'Riya Sen', 'BUSY', 1),
  (114, 'Vivek Oberoi', 'OFFLINE', 0),
  (115, 'Swati Patel', 'AVAILABLE', 0),
  (116, 'Gaurav Gupta', 'BUSY', 2),
  (117, 'Ishaan Khattar', 'BUSY', 1),
  (118, 'Kriti Sanon', 'AVAILABLE', 0),
  (119, 'Varun Dhawan', 'OFFLINE', 0),
  (120, 'Alia Bhatt', 'BUSY', 1);

-- =========================================================
-- STEP 2: INSERT ORDERS SECOND (Successfully links to Agents)
-- =========================================================
INSERT INTO orders (id, description, status, assigned_agent_id, created_at) VALUES
  -- Delivered Orders
  (1001, 'Electronics — Bandra to Andheri', 'DELIVERED', 101, NOW()),
  (1002, 'Groceries — Powai to Ghatkopar', 'DELIVERED', 104, NOW()),
  (1003, 'Pharma — Juhu to Versova', 'DELIVERED', 105, NOW()),
  (1004, 'Documents — Colaba to Fort', 'DELIVERED', 109, NOW()),
  (1005, 'Apparel — Dadar to Lower Parel', 'DELIVERED', 114, NOW()),
  (1006, 'Books — Chembur to Kurla', 'DELIVERED', 119, NOW()),

  -- Reassigned Orders
  (1007, 'Food — BKC to Santacruz', 'REASSIGNED', 103, NOW()),
  (1008, 'Hardware — Malad to Borivali', 'REASSIGNED', 108, NOW()),
  (1009, 'Cosmetics — Thane to Mulund', 'REASSIGNED', 111, NOW()),
  (1010, 'Pet Supplies — Vashi to Nerul', 'REASSIGNED', 115, NOW()),

  -- Reassignment Pending Orders
  (1011, 'Gifts — Worli to Prabhadevi', 'REASSIGNMENT_PENDING', 102, NOW()),
  (1012, 'Automotive — Kandivali to Goregaon', 'REASSIGNMENT_PENDING', 106, NOW()),
  (1013, 'Home Decor — Sion to Matunga', 'REASSIGNMENT_PENDING', 110, NOW()),
  (1014, 'Sports Gear — Churchgate to Marine Lines', 'REASSIGNMENT_PENDING', 116, NOW()),

  -- Active Assigned Orders
  (1015, 'Groceries — Saket to Hauz Khas', 'ASSIGNED', 102, NOW()),
  (1016, 'Electronics — Connaught Place to Karol Bagh', 'ASSIGNED', 103, NOW()),
  (1017, 'Pharma — Dwarka to Janakpuri', 'ASSIGNED', 106, NOW()),
  (1018, 'Documents — Noida Sec 62 to Sec 18', 'ASSIGNED', 106, NOW()),
  (1019, 'Food — Gurugram Phase 3 to Phase 5', 'ASSIGNED', 107, NOW()),
  (1020, 'Apparel — Vasant Kunj to Mehrauli', 'ASSIGNED', 110, NOW()),
  (1021, 'Books — Rohini to Pitampura', 'ASSIGNED', 112, NOW()),
  (1022, 'Hardware — Okhla to Nehru Place', 'ASSIGNED', 113, NOW()),
  (1023, 'Cosmetics — Gachibowli to Hitech City', 'ASSIGNED', 116, NOW()),
  (1024, 'Pet Supplies — Jubilee Hills to Banjara Hills', 'ASSIGNED', 117, NOW()),
  (1025, 'Gifts — Secunderabad to Begumpet', 'ASSIGNED', 120, NOW()),
  (1026, 'Electronics — Salt Lake to New Town', 'ASSIGNED', 102, NOW()),
  (1027, 'Groceries — Tollygunge to Ballygunge', 'ASSIGNED', 106, NOW()),
  (1028, 'Pharma — Park Street to Elgin Road', 'ASSIGNED', 110, NOW()),
  (1029, 'Food — Whitefield to Hoodi', 'ASSIGNED', 116, NOW()),
  (1030, 'Documents — Electronic City to Silk Board', 'ASSIGNED', 117, NOW());