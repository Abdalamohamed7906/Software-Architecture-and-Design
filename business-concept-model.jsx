const MONO = "'Courier New', monospace";

function Box({ x, y, w, label }) {
  const h = 36;
  return (
    <g>
      <rect x={x} y={y} width={w} height={h}
        fill="white" stroke="black" strokeWidth={1.5} />
      <text
        x={x + w / 2} y={y + 23}
        textAnchor="middle" fontSize={12}
        fontFamily={MONO} fill="black"
      >{label}</text>
    </g>
  );
}

function M({ x, y, t }) {
  return (
    <text x={x} y={y} fontSize={11} fontFamily={MONO} fill="black">{t}</text>
  );
}

function AssocLabel({ x, y, t }) {
  return (
    <text x={x} y={y} fontSize={11} fontFamily={MONO}
      fill="black" fontStyle="italic" textAnchor="middle">{t}</text>
  );
}

export default function App() {
  return (
    <div style={{ background: "white", padding: "16px 20px", fontFamily: MONO }}>
      <div style={{ fontSize: 13, fontWeight: "bold", marginBottom: 10, color: "black" }}>
        Business Concept Model — StockCompare
      </div>

      <svg width="100%" viewBox="0 0 770 445"
        style={{ display: "block", background: "white", border: "1px solid black" }}>

        {/* ═══════════ BOXES ═══════════════════════════════════════════════ */}

        {/* Row 1 */}
        <Box x={295}  y={15}  w={150} label="User" />

        {/* Row 2 — subtypes */}
        <Box x={20}   y={110} w={110} label="Visitor" />
        <Box x={175}  y={110} w={160} label="RegisteredUser" />
        <Box x={405}  y={110} w={110} label="Admin" />

        {/* Row 3 — RegisteredUser dependents + ShareSymbol */}
        <Box x={20}   y={235} w={130} label="Account" />
        <Box x={175}  y={235} w={130} label="SavedStock" />
        <Box x={490}  y={175} w={145} label="ShareSymbol" />

        {/* Row 4 — PriceData tree */}
        <Box x={490}  y={295} w={130} label="PriceData" />
        <Box x={640}  y={295} w={110} label="DateRange" />

        {/* Row 5 — PriceData dependents */}
        <Box x={20}   y={390} w={130} label="PriceGraph" />
        <Box x={175}  y={390} w={155} label="ComparisonResult" />
        <Box x={355}  y={390} w={120} label="ExportFile" />


        {/* ═══════════ A — GENERALISATION TREE (User → subtypes) ══════════ */}
        {/* Vertical trunk from User bottom */}
        <line x1={370} y1={51}  x2={370} y2={82}  stroke="black" strokeWidth={1.5} />
        {/* Horizontal bar */}
        <line x1={75}  y1={82}  x2={460} y2={82}  stroke="black" strokeWidth={1.5} />
        {/* Drop to Visitor */}
        <line x1={75}  y1={82}  x2={75}  y2={110} stroke="black" strokeWidth={1.5} />
        {/* Drop to RegisteredUser */}
        <line x1={255} y1={82}  x2={255} y2={110} stroke="black" strokeWidth={1.5} />
        {/* Drop to Admin */}
        <line x1={460} y1={82}  x2={460} y2={110} stroke="black" strokeWidth={1.5} />


        {/* ═══════════ B — RegisteredUser → Account  1:1 ══════════════════ */}
        <line x1={220} y1={146} x2={220} y2={215} stroke="black" strokeWidth={1.5} />
        <line x1={85}  y1={215} x2={220} y2={215} stroke="black" strokeWidth={1.5} />
        <line x1={85}  y1={215} x2={85}  y2={235} stroke="black" strokeWidth={1.5} />
        <M x={223} y={162} t="1" />
        <M x={88}  y={232} t="1" />


        {/* ═══════════ C — RegisteredUser → SavedStock  1:0..* ════════════ */}
        <line x1={290} y1={146} x2={290} y2={235} stroke="black" strokeWidth={1.5} />
        <M x={293} y={162}  t="1" />
        <M x={293} y={232}  t="0..*" />


        {/* ═══════════ D — User searches ShareSymbol  1:1..* ═════════════ */}
        {/* Horizontal right from User, then straight down to ShareSymbol top */}
        <line x1={445} y1={33}  x2={562} y2={33}  stroke="black" strokeWidth={1.5} />
        <line x1={562} y1={33}  x2={562} y2={175} stroke="black" strokeWidth={1.5} />
        <M x={447} y={30}  t="1" />
        <M x={565} y={172} t="1..*" />
        <AssocLabel x={503} y={27} t="searches" />


        {/* ═══════════ E — ShareSymbol → PriceData  1:1..* ═══════════════ */}
        <line x1={555} y1={211} x2={555} y2={295} stroke="black" strokeWidth={1.5} />
        <M x={558} y={226} t="1" />
        <M x={558} y={292} t="1..*" />


        {/* ═══════════ F — PriceData → DateRange  1:1 ════════════════════ */}
        <line x1={620} y1={313} x2={640} y2={313} stroke="black" strokeWidth={1.5} />
        <M x={600} y={310} t="1" />
        <M x={643} y={310} t="1" />


        {/* ═══════════ G — SavedStock refs PriceData  0..*:1..* ══════════ */}
        {/* L-shape: right → down → right */}
        <line x1={305} y1={253} x2={470} y2={253} stroke="black" strokeWidth={1.5} />
        <line x1={470} y1={253} x2={470} y2={313} stroke="black" strokeWidth={1.5} />
        <line x1={470} y1={313} x2={490} y2={313} stroke="black" strokeWidth={1.5} />
        <M x={308} y={250} t="0..*" />
        <M x={473} y={310} t="1..*" />
        <AssocLabel x={388} y={247} t="refs" />


        {/* ═══════════ H — PriceData → PriceGraph, ComparisonResult, ExportFile ═══ */}
        {/* Trunk from PriceData bottom */}
        <line x1={555} y1={331} x2={555} y2={365} stroke="black" strokeWidth={1.5} />
        {/* Horizontal bar */}
        <line x1={85}  y1={365} x2={555} y2={365} stroke="black" strokeWidth={1.5} />
        {/* Drop to PriceGraph */}
        <line x1={85}  y1={365} x2={85}  y2={390} stroke="black" strokeWidth={1.5} />
        {/* Drop to ComparisonResult */}
        <line x1={252} y1={365} x2={252} y2={390} stroke="black" strokeWidth={1.5} />
        {/* Drop to ExportFile */}
        <line x1={415} y1={365} x2={415} y2={390} stroke="black" strokeWidth={1.5} />
        {/* Multiplicities */}
        <M x={558} y={348} t="1..*" />
        <M x={88}  y={387} t="1" />
        <M x={255} y={387} t="0..*" />
        <M x={418} y={387} t="0..*" />

      </svg>

      <div style={{
        marginTop: 10, fontSize: 10, color: "#334155",
        fontFamily: MONO, lineHeight: 1.7,
        background: "#f9f9f9", border: "1px solid #ccc",
        borderRadius: 3, padding: "8px 12px"
      }}>
        <strong>Model Answer note:</strong> The business concept model shows all concepts relevant to the domain,
        whether or not they are managed directly by the application.
        User is abstract with three specialisations: Visitor, RegisteredUser, Admin.
        A RegisteredUser has one Account (1:1) and saves 0..* SavedStocks.
        User searches ShareSymbols (1:1..*). Each ShareSymbol has 1..* PriceData records.
        PriceData links to DateRange (1:1) and generates PriceGraph (1..*:1),
        ComparisonResult (1..*:0..*) and ExportFile (1..*:0..*).
        SavedStock references 1..* PriceData records.
      </div>
    </div>
  );
}
