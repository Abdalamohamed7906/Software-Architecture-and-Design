const MONO = "'Courier New', monospace";

function IBox({ x, y, w = 195, label }) {
  return (
    <g>
      <rect x={x} y={y} width={w} height={50}
        fill="white" stroke="black" strokeWidth={1.8} />
      <text x={x + w/2} y={y + 18} textAnchor="middle"
        fontSize={10} fontFamily={MONO} fill="black">
        {"<<interface type>>"}
      </text>
      <text x={x + w/2} y={y + 37} textAnchor="middle"
        fontSize={12} fontFamily={MONO} fill="black" fontWeight="bold">
        {label}
      </text>
    </g>
  );
}

function CBox({ x, y, w = 165, stereotype, label }) {
  const h = stereotype ? 50 : 36;
  return (
    <g>
      <rect x={x} y={y} width={w} height={h}
        fill="white" stroke="black" strokeWidth={1.5} />
      {stereotype && (
        <text x={x + w/2} y={y + 16} textAnchor="middle"
          fontSize={10} fontFamily={MONO} fill="black">
          {`<<${stereotype}>>`}
        </text>
      )}
      <text
        x={x + w/2}
        y={stereotype ? y + 35 : y + 23}
        textAnchor="middle" fontSize={12} fontFamily={MONO} fill="black">
        {label}
      </text>
    </g>
  );
}

function Diamond({ cx, cy }) {
  const s = 8;
  return (
    <polygon
      points={`${cx},${cy-s} ${cx+s*1.4},${cy} ${cx},${cy+s} ${cx-s*1.4},${cy}`}
      fill="black"
    />
  );
}

function M({ x, y, v }) {
  return <text x={x} y={y} fontSize={11} fontFamily={MONO} fill="black">{v}</text>;
}

function DashArrow({ x1, y1, x2, y2 }) {
  const dx = x2 - x1, dy = y2 - y1;
  const len = Math.sqrt(dx*dx + dy*dy);
  const ux = dx/len, uy = dy/len;
  const ax = x2 - ux*10, ay = y2 - uy*10;
  return (
    <>
      <line x1={x1} y1={y1} x2={ax} y2={ay}
        stroke="black" strokeWidth={1.5} strokeDasharray="6,4"/>
      <polygon
        points={`${x2},${y2} ${ax - uy*5},${ay + ux*5} ${ax + uy*5},${ay - ux*5}`}
        fill="black"/>
    </>
  );
}

export default function App() {
  return (
    <div style={{ background: "white", padding: "20px", fontFamily: MONO }}>
      <div style={{ fontSize: 14, fontWeight: "bold", marginBottom: 14, color: "black" }}>
        Business Type Model — StockCompare
      </div>

      {/* ── HEIGHT: 700, WIDTH: 860 ── */}
      <svg width="100%" viewBox="0 0 860 700"
        style={{ display: "block", background: "white", border: "1.5px solid black" }}>

        {/* ═══════════════════════════════════════════════════════════════
            INTERFACE BOXES — top of each sphere
        ═══════════════════════════════════════════════════════════════ */}
        <IBox x={15}  y={15} w={205} label="IAccountService" />
        <IBox x={330} y={15} w={200} label="IStockService" />
        <IBox x={640} y={15} w={205} label="IStockAnalysisService" />

        {/* ═══════════════════════════════════════════════════════════════
            SPHERE 1 — User Management
            RegisteredUser (core), Admin (core), Visitor (category), Account (type)
        ═══════════════════════════════════════════════════════════════ */}
        <rect x={10}  y={85} width={295} height={490} rx={38}
          fill="rgba(210,225,245,0.25)" stroke="black" strokeWidth={1.5}/>
        <text x={157} y={110} textAnchor="middle" fontSize={11}
          fontFamily={MONO} fill="black">User Management</text>

        <CBox x={35}  y={125} w={230} stereotype="core"     label="RegisteredUser" />
        <CBox x={35}  y={240} w={230} stereotype="core"     label="Admin" />
        <CBox x={35}  y={355} w={230} stereotype="category" label="Visitor" />
        <CBox x={35}  y={465} w={200} stereotype="type"     label="Account" />

        {/* IAccountService ——◆—— RegisteredUser */}
        <line x1={118} y1={65} x2={118} y2={125}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={118} cy={65}/>

        {/* RegisteredUser ——◆—— Admin  (Admin extends RegisteredUser) */}
        <line x1={150} y1={240} x2={150} y2={208}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={150} cy={208}/>
        <M x={153} y={235} v="*"/>
        <M x={153} y={214} v="1"/>

        {/* RegisteredUser ——◆—— Visitor */}
        <line x1={190} y1={355} x2={190} y2={295}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={190} cy={295}/>
        <M x={193} y={350} v="*"/>
        <M x={193} y={301} v="1"/>

        {/* RegisteredUser ——◆—— Account  1:1 */}
        <line x1={135} y1={465} x2={135} y2={395}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={135} cy={395}/>
        <M x={138} y={460} v="1"/>
        <M x={138} y={401} v="1"/>

        {/* ═══════════════════════════════════════════════════════════════
            SPHERE 2 — Stock Management
            ShareSymbol (core), SavedStock (type), PriceGraph (type)
        ═══════════════════════════════════════════════════════════════ */}
        <rect x={315} y={85} width={285} height={490} rx={38}
          fill="rgba(210,240,220,0.25)" stroke="black" strokeWidth={1.5}/>
        <text x={457} y={110} textAnchor="middle" fontSize={11}
          fontFamily={MONO} fill="black">Stock Management</text>

        <CBox x={340} y={125} w={230} stereotype="core" label="ShareSymbol" />
        <CBox x={340} y={270} w={230} stereotype="type" label="SavedStock" />
        <CBox x={340} y={415} w={230} stereotype="type" label="PriceGraph" />

        {/* IStockService ——◆—— ShareSymbol */}
        <line x1={430} y1={65} x2={430} y2={125}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={430} cy={65}/>

        {/* ShareSymbol ——◆—— SavedStock  1:0..* */}
        <line x1={455} y1={270} x2={455} y2={208}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={455} cy={208}/>
        <M x={458} y={265} v="0..*"/>
        <M x={458} y={214} v="1"/>

        {/* ShareSymbol ——◆—— PriceGraph  1:0..* */}
        <line x1={415} y1={415} x2={415} y2={353}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={415} cy={353}/>
        <M x={418} y={410} v="0..*"/>
        <M x={418} y={359} v="1"/>

        {/* ═══════════════════════════════════════════════════════════════
            SPHERE 3 — Price / Data Management
            PriceData (core), DateRange (type),
            ComparisonResult (type), ExportFile (type)
        ═══════════════════════════════════════════════════════════════ */}
        <rect x={615} y={85} width={235} height={530} rx={38}
          fill="rgba(250,245,210,0.25)" stroke="black" strokeWidth={1.5}/>
        <text x={732} y={110} textAnchor="middle" fontSize={11}
          fontFamily={MONO} fill="black">Price / Data Mgmt</text>

        <CBox x={635} y={125} w={195} stereotype="core" label="PriceData" />
        <CBox x={635} y={260} w={195} stereotype="type" label="DateRange" />
        <CBox x={635} y={380} w={195} stereotype="type" label="ComparisonResult" />
        <CBox x={635} y={500} w={195} stereotype="type" label="ExportFile" />

        {/* IStockAnalysisService ——◆—— PriceData */}
        <line x1={742} y1={65} x2={742} y2={125}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={742} cy={65}/>

        {/* PriceData ——◆—— DateRange  1:1 */}
        <line x1={732} y1={260} x2={732} y2={208}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={732} cy={208}/>
        <M x={735} y={255} v="1"/>
        <M x={735} y={214} v="1"/>

        {/* PriceData ——◆—— ComparisonResult  1:0..* */}
        <line x1={718} y1={380} x2={718} y2={330}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={718} cy={330}/>
        <M x={721} y={375} v="0..*"/>
        <M x={721} y={336} v="1..*"/>

        {/* PriceData ——◆—— ExportFile  1:0..* */}
        <line x1={755} y1={500} x2={755} y2={450}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={755} cy={450}/>
        <M x={758} y={495} v="0..*"/>
        <M x={758} y={456} v="1..*"/>

        {/* ═══════════════════════════════════════════════════════════════
            CROSS-BOUNDARY ASSOCIATIONS (dashed arrows)
        ═══════════════════════════════════════════════════════════════ */}

        {/* RegisteredUser —saves—> SavedStock */}
        <DashArrow x1={265} y1={145} x2={340} y2={145}/>
        <M x={267} y={140} v="1"/>
        <M x={313} y={140} v="0..*"/>
        <text x={302} y={162} textAnchor="middle" fontSize={10}
          fontFamily={MONO} fill="black" fontStyle="italic">saves</text>

        {/* ShareSymbol —has—> PriceData */}
        <DashArrow x1={570} y1={145} x2={635} y2={145}/>
        <M x={572} y={140} v="1"/>
        <M x={608} y={140} v="1..*"/>
        <text x={602} y={162} textAnchor="middle" fontSize={10}
          fontFamily={MONO} fill="black" fontStyle="italic">has</text>

        {/* ═══════════════════════════════════════════════════════════════
            LEGEND
        ═══════════════════════════════════════════════════════════════ */}
        <rect x={10} y={632} width={840} height={58}
          fill="white" stroke="#aaa" strokeWidth={1} rx={3}/>
        <text x={20} y={650} fontSize={10} fontFamily={MONO}
          fill="black" fontWeight="bold">Legend:</text>

        <Diamond cx={108} cy={660}/>
        <text x={120} y={664} fontSize={10} fontFamily={MONO} fill="black">
          Composition / ownership
        </text>

        <line x1={310} y1={660} x2={355} y2={660}
          stroke="black" strokeWidth={1.5} strokeDasharray="5,3"/>
        <polygon points="355,655 345,660 355,665" fill="black"/>
        <text x={362} y={664} fontSize={10} fontFamily={MONO} fill="black">
          Cross-boundary association
        </text>

        <rect x={565} y={652} width={14} height={14}
          fill="white" stroke="black" strokeWidth={1.2}/>
        <text x={584} y={663} fontSize={10} fontFamily={MONO} fill="black">
          Class (with stereotype inside)
        </text>

        <text x={20} y={682} fontSize={10} fontFamily={MONO} fill="black">
          {"<<core>> = stands alone   <<category>> = classifies   <<type>> = depends on core"}
        </text>

      </svg>

      <div style={{
        marginTop: 10, fontSize: 10, color: "#334155",
        fontFamily: MONO, lineHeight: 1.7,
        background: "#f9f9f9", border: "1px solid #ccc",
        borderRadius: 3, padding: "8px 12px"
      }}>
        <strong>Sphere 1 — User Management:</strong> RegisteredUser and Admin are both
        {" "}{"<<core>>"} types. Admin extends RegisteredUser (Admin is also a user with elevated privileges).
        Visitor {"<<category>>"} classifies the user type. Account {"<<type>>"} depends on RegisteredUser.
        Interface: IAccountService.
        {" | "}
        <strong>Sphere 2 — Stock Management:</strong> ShareSymbol {"<<core>>"} owns SavedStock
        and PriceGraph as {"<<type>>"} dependents. Interface: IStockService.
        {" | "}
        <strong>Sphere 3 — Price / Data Mgmt:</strong> PriceData {"<<core>>"} owns DateRange,
        ComparisonResult, and ExportFile as {"<<type>>"} dependents. Interface: IStockAnalysisService.
      </div>
    </div>
  );
}
