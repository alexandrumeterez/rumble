(:JIQS: ShouldRun; Output="" :)
let $x := annotate(
  for $i in 1 to 5
  return {"foo":$i, "bar":($i+1)},
  {"foo":"integer", "bar":"integer"}
)
return
project($x, ("foo", "foobar", "bar"))
