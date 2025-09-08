-- Migration script to add icon support to existing Category table
-- Run this if you already have a Category table without icon fields

USE jpast4;

-- Add icon fields to Category table if they don't exist
ALTER TABLE Category 
ADD COLUMN IF NOT EXISTS icon_path VARCHAR(1000) AFTER cate_name,
ADD COLUMN IF NOT EXISTS icon_filename VARCHAR(255) AFTER icon_path;

-- Update existing records to have NULL icon values (if needed)
UPDATE Category SET icon_path = NULL, icon_filename = NULL 
WHERE icon_path = '' OR icon_filename = '';

-- Add index for better performance
CREATE INDEX IF NOT EXISTS idx_category_icon_filename ON Category(icon_filename);

-- Show updated table structure
DESCRIBE Category;

-- Show all categories
SELECT cate_id, cate_name, 
       CASE 
         WHEN icon_filename IS NOT NULL THEN CONCAT('Has icon: ', icon_filename)
         ELSE 'No icon'
       END as icon_status
FROM Category
ORDER BY cate_id;