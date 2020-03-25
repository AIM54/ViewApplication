    SELECT  ParentGroup.[NameCn],ChildGroup.[GroupNo],ChildGroup.[NameCn],PointTable.[NameCn],ParentMapTable.[NameCn]

    FROM CHECK_RE_GROUP ParentGroup

   JOIN CHECK_RE_GROUP ChildGroup

   ON  ChildGroup.[ParentNo]=ParentGroup.[GroupNo]

   JOIN CHECK_RE_PROD_GROUP  ProductGroupTable

   ON ProductGroupTable.[GroupNo]=ChildGroup.[GroupNo] AND ProductGroupTable.ProdNo ='035'

   JOIN CHECK_RE_PROD_GROUP_POINT GroupPointTable

   ON GroupPointTable.ProdNo=ProductGroupTable.ProdNo  AND   GroupPointTable.GroupNo=ProductGroupTable.GroupNo

   JOIN CHECK_RE_POINT PointTable

   ON GroupPointTable.PointNo=PointTable.PointNo

   LEFT JOIN CHECK_RE_MAPPING_LISTATTR PointMapTable

   ON  GroupPointTable.ListNo= PointMapTable.ListNo

   LEFT JOIN CHECK_RE_MAPPING_ATTR ParentMapTable

   ON PointMapTable.MapNo =ParentMapTable.MapNo

   ORDER BY ChildGroup.[GroupNo],ProductGroupTable.Sequence , GroupPointTable.Sequence ,PointMapTable.Sequence

