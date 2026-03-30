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

      {/* ── HEIGHT: 730, WIDTH: 1140 (4 spheres) ── */}
      <svg width="100%" viewBox="0 0 1140 730"
        style={{ display: "block", background: "white", border: "1.5px solid black" }}>

        {/* ═══════════════════════════════════════════════════════════════
            INTERFACE BOXES — top of each sphere (4 total)
        ═══════════════════════════════════════════════════════════════ */}
        <IBox x={15}  y={15} w={210} label="IAccountService" />
        <IBox x={293} y={15} w={165} label="IAdminService" />
        <IBox x={493} y={15} w={185} label="IStockService" />
        <IBox x={825} y={15} w={295} label="IStockAnalysisService" />

        {/* ═══════════════════════════════════════════════════════════════
            SPHERE 1 — User Management
            RegisteredUser (core), Visitor (category), Account (type)
            [Admin removed — now its own sphere]
        ═══════════════════════════════════════════════════════════════ */}
        <rect x={10}  y={85} width={270} height={430} rx={38}
          fill="rgba(210,225,245,0.25)" stroke="black" strokeWidth={1.5}/>
        <text x={145} y={110} textAnchor="middle" fontSize={11}
          fontFamily={MONO} fill="black">User Management</text>

        <CBox x={25}  y={125} w={235} stereotype="core"     label="RegisteredUser" />
        <CBox x={25}  y={255} w={235} stereotype="category" label="Visitor" />
        <CBox x={25}  y={370} w={200} stereotype="type"     label="Account" />

        {/* IAccountService ——◆—— RegisteredUser */}
        <line x1={120} y1={65} x2={120} y2={125}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={120} cy={65}/>

        {/* RegisteredUser ——◆—— Visitor */}
        <line x1={155} y1={255} x2={155} y2={210}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={155} cy={210}/>
        <M x={158} y={250} v="*"/>
        <M x={158} y={216} v="1"/>

        {/* RegisteredUser ——◆—— Account  1:1 */}
        <line x1={110} y1={370} x2={110} y2={300}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={110} cy={300}/>
        <M x={113} y={365} v="1"/>
        <M x={113} y={306} v="1"/>

        {/* ═══════════════════════════════════════════════════════════════
            SPHERE 2 — Admin Management  ← NEW SPHERE
            Admin (core) — governed by IAdminService
        ═══════════════════════════════════════════════════════════════ */}
        <rect x={285} y={85} width={190} height={200} rx={38}
          fill="rgba(245,215,240,0.30)" stroke="black" strokeWidth={1.5}/>
        <text x={380} y={110} textAnchor="middle" fontSize={11}
          fontFamily={MONO} fill="black">Admin Management</text>

        <CBox x={300} y={125} w={160} stereotype="core" label="Admin" />

        {/* IAdminService ——◆—— Admin */}
        <line x1={375} y1={65} x2={375} y2={125}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={375} cy={65}/>

        {/* ═══════════════════════════════════════════════════════════════
            SPHERE 3 — Stock Management
            ShareSymbol (core), SavedStock (type), PriceGraph (type)
        ═══════════════════════════════════════════════════════════════ */}
        <rect x={485} y={85} width={275} height={490} rx={38}
          fill="rgba(210,240,220,0.25)" stroke="black" strokeWidth={1.5}/>
        <text x={622} y={110} textAnchor="middle" fontSize={11}
          fontFamily={MONO} fill="black">Stock Management</text>

        <CBox x={500} y={125} w={230} stereotype="core" label="ShareSymbol" />
        <CBox x={500} y={270} w={230} stereotype="type" label="SavedStock" />
        <CBox x={500} y={415} w={230} stereotype="type" label="PriceGraph" />

        {/* IStockService ——◆—— ShareSymbol */}
        <line x1={585} y1={65} x2={585} y2={125}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={585} cy={65}/>

        {/* ShareSymbol ——◆—— SavedStock  1:0..* */}
        <line x1={620} y1={270} x2={620} y2={208}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={620} cy={208}/>
        <M x={623} y={265} v="0..*"/>
        <M x={623} y={214} v="1"/>

        {/* ShareSymbol ——◆—— PriceGraph  1:0..* */}
        <line x1={575} y1={415} x2={575} y2={353}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={575} cy={353}/>
        <M x={578} y={410} v="0..*"/>
        <M x={578} y={359} v="1"/>

        {/* ═══════════════════════════════════════════════════════════════
            SPHERE 4 — Price / Data Management
            PriceData (core), DateRange (type),
            ComparisonResult (type), ExportFile (type)
        ═══════════════════════════════════════════════════════════════ */}
        <rect x={770} y={85} width={355} height={540} rx={38}
          fill="rgba(250,245,210,0.25)" stroke="black" strokeWidth={1.5}/>
        <text x={947} y={110} textAnchor="middle" fontSize={11}
          fontFamily={MONO} fill="black">Price / Data Mgmt</text>

        <CBox x={865} y={125} w={200} stereotype="core" label="PriceData" />
        <CBox x={865} y={265} w={200} stereotype="type" label="DateRange" />
        <CBox x={865} y={385} w={200} stereotype="type" label="ComparisonResult" />
        <CBox x={865} y={505} w={200} stereotype="type" label="ExportFile" />

        {/* IStockAnalysisService ——◆—— PriceData */}
        <line x1={965} y1={65} x2={965} y2={125}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={965} cy={65}/>

        {/* PriceData ——◆—— DateRange  1:1 */}
        <line x1={952} y1={265} x2={952} y2={208}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={952} cy={208}/>
        <M x={955} y={260} v="1"/>
        <M x={955} y={214} v="1"/>

        {/* PriceData ——◆—— ComparisonResult  1:0..* */}
        <line x1={930} y1={385} x2={930} y2={330}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={930} cy={330}/>
        <M x={933} y={380} v="0..*"/>
        <M x={933} y={336} v="1..*"/>

        {/* PriceData ——◆—— ExportFile  1:0..* */}
        <line x1={975} y1={505} x2={975} y2={450}
          stroke="black" strokeWidth={1.5}/>
        <Diamond cx={975} cy={450}/>
        <M x={978} y={500} v="0..*"/>
        <M x={978} y={456} v="1..*"/>

        {/* ═══════════════════════════════════════════════════════════════
            CROSS-BOUNDARY ASSOCIATIONS (dashed arrows)
        ═══════════════════════════════════════════════════════════════ */}

        {/* RegisteredUser —extends—> Admin  (short hop, Sphere1 → Sphere2) */}
        <DashArrow x1={260} y1={148} x2={300} y2={148}/>
        <text x={280} y={165} textAnchor="middle" fontSize={9}
          fontFamily={MONO} fill="black" fontStyle="italic">extends</text>

        {/* RegisteredUser —saves—> SavedStock  (Sphere1 → Sphere3, diagonal) */}
        <DashArrow x1={260} y1={160} x2={500} y2={285}/>
        <M x={262} y={156} v="1"/>
        <M x={474} y={282} v="0..*"/>
        <text x={390} y={195} textAnchor="middle" fontSize={10}
          fontFamily={MONO} fill="black" fontStyle="italic">saves</text>

        {/* ShareSymbol —has—> PriceData  (Sphere3 → Sphere4) */}
        <DashArrow x1={730} y1={148} x2={865} y2={148}/>
        <M x={732} y={143} v="1"/>
        <M x={838} y={143} v="1..*"/>
        <text x={797} y={165} textAnchor="middle" fontSize={10}
          fontFamily={MONO} fill="black" fontStyle="italic">has</text>

        {/* ═══════════════════════════════════════════════════════════════
            LEGEND
        ═══════════════════════════════════════════════════════════════ */}
        <rect x={10} y={645} width={1115} height={58}
          fill="white" stroke="#aaa" strokeWidth={1} rx={3}/>
        <text x={20} y={663} fontSize={10} fontFamily={MONO}
          fill="black" fontWeight="bold">Legend:</text>

        <Diamond cx={108} cy={673}/>
        <text x={120} y={677} fontSize={10} fontFamily={MONO} fill="black">
          Composition / ownership
        </text>

        <line x1={310} y1={673} x2={355} y2={673}
          stroke="black" strokeWidth={1.5} strokeDasharray="5,3"/>
        <polygon points="355,668 345,673 355,678" fill="black"/>
        <text x={362} y={677} fontSize={10} fontFamily={MONO} fill="black">
          Cross-boundary association
        </text>

        <rect x={570} y={665} width={14} height={14}
          fill="white" stroke="black" strokeWidth={1.2}/>
        <text x={589} y={676} fontSize={10} fontFamily={MONO} fill="black">
          Class (with stereotype inside)
        </text>

        <text x={20} y={695} fontSize={10} fontFamily={MONO} fill="black">
          {"<<core>> = stands alone   <<category>> = classifies   <<type>> = depends on core"}
        </text>

      </svg>

      <div style={{
        marginTop: 10, fontSize: 10, color: "#334155",
        fontFamily: MONO, lineHeight: 1.7,
        background: "#f9f9f9", border: "1px solid #ccc",
        borderRadius: 3, padding: "8px 12px"
      }}>
        <strong>Sphere 1 — User Management:</strong> RegisteredUser is the {"<<core>>"} type.
        Visitor {"<<category>>"} classifies the user type. Account {"<<type>>"} depends on RegisteredUser (1:1).
        Interface: IAccountService.
        {" | "}
        <strong>Sphere 2 — Admin Management:</strong> Admin is a {"<<core>>"} type in its own sphere,
        governed by IAdminService. A cross-boundary arrow marks that Admin extends RegisteredUser.
        {" | "}
        <strong>Sphere 3 — Stock Management:</strong> ShareSymbol {"<<core>>"} owns SavedStock
        and PriceGraph as {"<<type>>"} dependents. Interface: IStockService.
        {" | "}
        <strong>Sphere 4 — Price / Data Mgmt:</strong> PriceData {"<<core>>"} owns DateRange,
        ComparisonResult, and ExportFile as {"<<type>>"} dependents. Interface: IStockAnalysisService.
      </div>
    </div>
  );
}
