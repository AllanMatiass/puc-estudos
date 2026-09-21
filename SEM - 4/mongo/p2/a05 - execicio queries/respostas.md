{ano: 1999}
{ano: {$gte: 2010}}
{ano: {$gte: 2010}, runtime: {$lte: 150}}
{rating: 5.5}
{languages: "french"}
{languages: ["french"]}
{languages: { $size: 3 }}
não, size não aceita outros atributos como $gt


