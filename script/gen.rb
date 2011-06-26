#!/usr/bin/ruby
require 'erb'

class_name = "Author"
params = ["firstName:String", "lastName:String", "email:String"]
m = params.map { |e| [e.split(":")[0], e.split(":")[1]] }

template = <<EOS
@BeanInfo
case class <%= class_name %>(
<% params.each do |p| -%>
  var <%= p.sub(":", ": ")  %>
<% end %>  val id: Int = 0
) extends BaseModel {
  def this() = this("","","")
}
EOS

template2 = <<EOS
  implicit def <%=class_name%>Format: Format[<%=class_name%>] = 
    asProduct<%=m.size + 1%>(<% m.each {|e|%>"<%=e[0]%>",<%}%>"id")(<%=class_name%>)(<%=class_name%>.unapply(_).get)
EOS

#f = open("src/main/scala/Schema.scala")
#lines = f.readlines
#lines.each {|l|
#  if Regexp.new("object Library") =~ l
#    puts l
#  end
#}
#f.close

erb = ERB.new(template, nil, "-")
puts erb.result(binding)
