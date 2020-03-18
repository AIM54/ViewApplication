  select PointTable.PointNo, PointTable.PointCode, PointTable.NameCn,PointTable.NameEn from

  CHECK_RE_GROUP GroupTable join CHECK_RE_PROD_GROUP_POINT ProductAndGroup

  on GroupTable.[GroupNo]=ProductAndGroup.[GroupNo] and ProductAndGroup.[ProdNo]='035' and GroupTable.GroupNo='3131'

  join  CHECK_RE_POINT PointTable where PointTable.PointNo=ProductAndGroup.[PointNo]

  order by ProductAndGroup.Sequence
