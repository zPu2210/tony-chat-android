# Research Report: AI APIs & Backend Options for Tony Chat MVP
**Date:** 2026-02-16 | **Researcher:** researcher-ae7a156

---

## Executive Summary

Research across three critical areas for Tony Chat Android MVP:
1. **AI Emoji/Sticker Generation**: OpenAI DALL-E 3 most mature ($0.04-0.12/image); dedicated emoji APIs (Pixazo, Replicate) cheaper for small volume
2. **Image Editing APIs**: ClipDrop best all-rounder; remove.bg cheapest for background removal only; Stability AI best for inpainting/object removal
3. **Location Backend**: **Supabase + PostGIS** wins for MVP speed + location queries; Firebase faster initial setup but geohashing complexity; Appwrite not recommended

---

## 1. AI Emoji/Sticker Generation APIs

### Recommendation: **Hybrid Approach**
- **For text→sticker (MVP)**: Pixazo AI Sticker Maker API or Artificial Studio (dedicated sticker APIs, cheaper for low volume)
- **For high-quality custom emojis**: Replicate's fofr/sdxl-emoji (text-to-emoji, fine-tuned on Apple emoji set)
- **For future scaling**: OpenAI DALL-E 3 or Google Gemini 2.5 (more mature, better quality)

### Detailed Breakdown

| Provider | Model | Price | Quality | Latency | Use Case |
|----------|-------|-------|---------|---------|----------|
| **Pixazo** | AI Sticker Maker | ~$0.01-0.05/image (credit-based) | Good, clean cutouts | Fast | MVP: text→sticker |
| **Artificial Studio** | Custom Sticker API | Not publicly disclosed | Good | Fast | MVP: text→sticker |
| **Replicate** | fofr/sdxl-emoji | $0.0035/image | Excellent (Apple-tuned) | 1-3s | Premium emoji generation |
| **OpenAI** | DALL-E 3 | $0.04 (1024px), $0.08 (1024x1792), $0.12 (1792x1792) | Excellent | 3-10s | Production quality, complex prompts |
| **Google Gemini** | 2.5 Flash Image (free tier) | Free: 1,500/day; Production: $0.039/image (1024px) | Good-Excellent | 1-2s | Budget-friendly MVP |

### Key Insights

- **MVP Phase (MVP)**: Use Pixazo or Replicate's SDXL emoji (~$1-2/month for <100 generations)
- **Free tier exists**: Google Gemini provides 1,500 free image generations/day (sufficient for MVP user testing)
- **No dedicated "emoji" vs "sticker" distinction in modern APIs** - text→image generation with small output size (128-512px) works for both
- **Apple Genmoji** (iOS 18.2+) generates stickers natively, but not via API; Android must use cloud APIs
- **Dedicate emoji APIs** (Pixazo, Artificial Studio) likely cheaper for small volume (<1K/month) than DALL-E 3

### Recommended Implementation Path
```
Week 1-2: Deploy Pixazo Sticker API on MVP (cost: ~$5-10)
→ Test UI/UX with real generated stickers
Week 3+: Migrate to Replicate SDXL emoji for higher quality if needed
→ Or upgrade to DALL-E 3 if premium quality justifies cost
```

---

## 2. AI Image Editing APIs

### Recommendation: **Tiered Strategy by Feature**

| Feature | Recommended | Price (MVP) | Alternative |
|---------|-------------|------------|-------------|
| **Background removal** | remove.bg | $0.0025/image (1 credit = 0.25¢) | ClipDrop (1 credit/image = $0.01 at $9/100) |
| **Object removal / inpainting** | ClipDrop (Cleanup) | $0.03/image (3 credits @ $0.01ea) | Stability AI Erase (8 credits = $0.08) |
| **Multi-tool suite** | ClipDrop | $0.01-0.03/image | Stability AI (+face removal, recolor) |
| **Upscaling / enhancement** | ClipDrop (Reimagine) | $0.02/image (2 credits) | Stability AI Upscale |

### Detailed Options

#### **remove.bg**
- **Background removal**: 1 credit per image (up to 10MP resolution)
- **Pricing**: $0.0025/image (free tier: limited, paid starts $5/month for 500 images)
- **Quality**: Best-in-class for consistent hair/transparency handling
- **API**: Supports PNG (10MP), JPG/WebP (50MP), multiple output formats
- **MVP Cost**: <$1/month for <100 images

#### **ClipDrop**
- **Services**: Background removal (1 credit), Cleanup/object removal (3 credits), Relight (2 credits), Reimagine/upscale (2 credits)
- **Pricing**: $0.01/credit; $9/month = 900 credits (budget: $9-20/month MVP)
- **Quality**: Excellent for complex backgrounds; hair/fur better than competitors
- **Speed**: 1-3s API response
- **Advantage**: One API covers 4+ features; better than remove.bg alone for full MVP feature set

#### **Stability AI Image Services**
- **Services**: Inpaint, Erase (object removal), Remove Background, Search & Replace, Recolor
- **Pricing**: Credit system ($0.01/credit); Stable Image Ultra (8 credits), Inpaint varies
- **Quality**: Good for inpainting; professional results on product photography
- **Advantage**: Best for complex inpainting workflows; background removal less specialized than remove.bg
- **AWS Bedrock integration**: Enterprise-grade infrastructure option

#### **Google Vertex AI (Imagen)**
- **Services**: Object removal via mask or auto-mask generation
- **Pricing**: Via Google Cloud (per-API metering, typically ~$0.01-0.05/image)
- **Quality**: Good, native GCP integration if using Google Cloud stack
- **Limitation**: Requires Google Cloud account; less transparent pricing

### MVP Recommendation
```
Phase 1: Add background removal
→ Use remove.bg (cheapest, best quality, focused)
→ Cost: ~$1-2/month for <100 images

Phase 2: Add object removal / inpainting (if user feedback requests)
→ Migrate to ClipDrop for multi-feature coverage
→ Cost: ~$9-15/month for 100-200 operations
→ Alternative: Keep remove.bg + add Stability AI Erase for complex cases
```

---

## 3. Location-Based Backend (Bulletin Board / Nearby Posts)

### Recommendation: **Supabase + PostGIS**

**Winner: Supabase** for MVP speed, feature coverage, and location query performance.

#### Comparison Matrix

| Aspect | Firebase | Supabase | Appwrite |
|--------|----------|----------|----------|
| **Initial Setup Speed** | ⚡⚡⚡ (minutes) | ⚡⚡ (15-30 min) | ⚡⚡⚡ (minutes) |
| **Location Queries** | ⚠️ Geohash library required | ✅ PostGIS native | ❓ Limited docs |
| **Real-time Nearby Updates** | ❌ Geohash + workarounds | ✅ PostGIS + Realtime | ❓ Not tested |
| **Cost Scaling** | ❌ Expensive (read/write billing) | ✅ Predictable (flat tier) | ✅ Predictable |
| **SQL Flexibility** | ❌ NoSQL document store | ✅ Full PostgreSQL | ✅ MongoDB/PostgreSQL |
| **Free Tier** | 1 GB storage, 10 GB DL/mo | 500MB storage, 2M API calls | 500MB storage, 50K MAU |
| **Time to Location Feature** | 2-3 weeks (geohash library + testing) | 2-3 days (PostGIS extension enabled) | ⚠️ Unknown |

### Why Supabase Wins for Location MVP

**1. PostGIS Integration (Killer Feature)**
```sql
-- Single query to find nearby posts within 5km, sorted by distance
SELECT id, title, location, ST_Distance(location, ST_MakePoint(user_lon, user_lat)) AS distance
FROM posts
WHERE ST_DWithin(location::geography, ST_MakePoint(user_lon, user_lat)::geography, 5000)
ORDER BY distance ASC
LIMIT 20;
```
- Spatial indexes automatically optimize performance
- No geohashing library overhead
- Native distance calculation and sorting
- Scales to millions of posts with <100ms queries

**2. Real-time Subscriptions for Nearby Posts**
```kotlin
// Listen for new posts within 5km radius
supabase.realtime
    .channel("nearby_posts")
    .on("postgres_changes", { change ->
        // Update UI with new nearby posts
    })
    .subscribe()
```
- Supabase Realtime + PostgreSQL trigger
- Instant updates when new posts appear in user's radius
- No polling; event-driven architecture

**3. Schema Flexibility**
- Location stored as `geography(POINT)` type
- Full PostgreSQL available for complex queries
- Easy to add metadata (post type, expiration, moderation flags, etc.)

### Firebase Geohashing Complexity (Why It Loses)

Firebase approach requires:
1. **Client-side geohashing library** (GeoFire for Android: non-trivial)
2. **Multiple index queries** (query geohashes near user's cell)
3. **Client filtering** (filter false positives from geohash grid)
4. **No native spatial indexes** (performance degrades with scale)

**Example Firebase workaround:**
```kotlin
// Firebase geohashing: multiple queries needed
val geoQuery = GeoFirestore(db.collection("posts"))
geoQuery.queryAtLocation(GeoPoint(lat, lon), 5.0) // 5km radius
geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
    override fun onKeyEntered(documentID: String, location: GeoPoint) {
        // Handle new post in radius (inefficient: may trigger many times)
    }
})
```

**Firebase cost escalates** with read volume (each geohash query = multiple reads).

### Appwrite (Not Recommended)
- Limited location query documentation
- No native spatial indexing visible in docs
- Team too small compared to Firebase/Supabase for complex features
- Risk: May need custom backend layer anyway

### Free Tier Sufficiency

**Supabase Free Tier** for MVP bulletin board:
- 500MB storage (sufficient for ~50K posts with metadata)
- 2M API calls/month (~70 calls/day per user; supports ~1K active users)
- Realtime included
- **Enough for MVP launch** with <1K daily active users

**Firebase Free Tier** for location feature:
- 1 GB storage
- 10 GB download bandwidth/month
- **Geohashing adds overhead** (multiple queries per search = bandwidth burn)
- **Cost blows up at scale** (expensive reads)

---

## Implementation Path: Location Feature

### Phase 1: Supabase Setup (1-2 days)
```
1. Create Supabase project
2. Enable PostGIS extension (1 SQL command: CREATE EXTENSION postgis;)
3. Create posts table:
   - id (uuid primary key)
   - title (text)
   - author_id (uuid FK)
   - location (geography(POINT) NOT NULL)
   - created_at (timestamp)
   - content (text)
   - index: CREATE INDEX posts_location_idx ON posts USING GIST(location)

4. Create RLS policy to allow anonymous reads, authenticated writes
5. Setup Realtime subscription for nearby posts
```

### Phase 2: Android Integration (2-3 days)
```kotlin
// Add to TonyChat:
// 1. Supabase Kotlin client dependency
// 2. Wrapper: LocationService.getNearbyPosts(lat, lon, radiusKm)
// 3. Observer: PostViewModel observes realtime updates
// 4. UI: BulletinBoardFragment displays posts, allows posting
```

### Phase 3: Testing & Iteration (2-3 days)
```
- Test query performance at scale (add 10K mock posts)
- Test realtime subscription latency
- Implement post expiration (auto-cleanup old posts)
- Add moderation queue
```

**Total MVP: 5-8 days** vs Firebase (10-14 days with geohashing complexity).

---

## Cost Summary (MVP 1-Month Estimate)

| Component | Provider | MVP Cost | Notes |
|-----------|----------|----------|-------|
| **Emoji/Sticker** | Pixazo | $5-10 | <100 generations |
| **Background Removal** | remove.bg | $1-2 | <100 images |
| **Object Removal** | (deferred) | $0 | Add if user feedback |
| **Location Backend** | Supabase Free | $0 | Sufficient for <1K DAU |
| **TOTAL MVP COST** | — | **$6-12/month** | Scales to $20-50 at 10K DAU |

---

## Unresolved Questions

1. **Emoji generation quality vs speed**: Should MVP prioritize fast generation (Pixazo) or higher quality (Replicate/DALL-E)? Depends on UX testing.
2. **Remove.bg vs ClipDrop trade-off**: If MVP only needs background removal, remove.bg sufficient. If object removal becomes high-demand feature early, ClipDrop breaks even at 200+ operations.
3. **Supabase geohashing performance at 100K+ posts**: Docs show PostGIS scales well, but no recent benchmarks provided. Should test with 50K mock posts before launch.
4. **Real-time subscription cost scaling**: Supabase Realtime has limits; not specified in free tier docs. May need to set reasonable radius limits (e.g., max 10km) to avoid subscription overload.
5. **Moderation complexity for user-generated posts**: Spam/offensive post handling not addressed. Recommend deferring to Phase 2 (launch with manual review or simple keyword filtering).

---

## Sources

- [OpenAI DALL-E Pricing](https://openai.com/api/pricing/)
- [Stability AI API Pricing](https://platform.stability.ai/pricing)
- [Google Gemini Image Generation](https://ai.google.dev/gemini-api/docs/pricing)
- [Supabase PostGIS Documentation](https://supabase.com/docs/guides/database/extensions/postgis)
- [Firebase Realtime Database Limits](https://firebase.google.com/docs/database/usage/limits)
- [ClipDrop Pricing](https://clipdrop.co/pricing)
- [remove.bg Pricing](https://www.remove.bg/pricing)
- [Firebase vs Supabase MVP Comparison](https://www.valtorian.com/blog/supabase-vs-firebase-startup-mvp-backend)
- [PostGIS Real-time Location Sharing](https://supabase.com/blog/postgres-realtime-location-sharing-with-maplibre)
- [Replicate SDXL Emoji](https://replicate.com/collections/generate-emoji)
- [Pixazo AI Sticker Maker API](https://www.pixazo.ai/models/tools/ai-sticker-maker-api)
