#import "@preview/codly:1.3.0": codly-init, local

#let listing(title, body) = {
  // Codly только внутри листинга
  show: codly-init.with()

  // Подпись и расположение
  set figure.caption(position: top, separator: [ — ])
  show figure.caption.where(kind: "listing"): set align(left)
  show figure.where(kind: "listing"): set block(breakable: true)
  set align(left)

  figure(
    local(
      body,
      number-format: none,
      zebra-fill: none,
      display-icon: false,
      stroke: 0.5pt + black,
      radius: 0pt,
      inset: (x: 6pt, y: 3pt),
      breakable: true,
      smart-indent: true,
    ),
    kind: "listing",
    supplement: [Листинг],
    caption: title,
  )
}
