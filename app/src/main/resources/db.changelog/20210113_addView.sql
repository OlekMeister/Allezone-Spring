CREATE OR REPLACE VIEW auction_view as
SELECT
    au.id as auction_id,
    au.title as auctionTitle,
    au.price as price,
    ca.name as category,
    ap.name as miniature
FROM
    auction au, auction_photo ap, category ca
WHERE ap.position = 1 AND ap.auction_id=au.id AND au.category_id=ca.id;

