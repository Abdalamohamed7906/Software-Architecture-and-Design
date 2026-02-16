# How to Run the Code in IntelliJ

## Step-by-Step Setup

### Step 1: Extract the ZIP file
1. Download `stock-compare.zip`
2. Right-click → Extract All
3. Extract to a location like `C:\Users\YourName\Projects\stock-compare`

### Step 2: Open in IntelliJ
1. Open IntelliJ IDEA
2. Click **File → Open**
3. Navigate to the extracted `stock-compare` folder
4. Click **OK**
5. If prompted "Trust and Open Project?" → Click **Trust Project**

### Step 3: Configure Project SDK
1. Go to **File → Project Structure** (or press `Ctrl+Alt+Shift+S`)
2. Under **Project**, select **Project SDK**
3. If you see Java 11 or higher → Select it
4. If you don't see any SDK:
   - Click **Add SDK → Download JDK**
   - Choose version 11 or higher
   - Click **Download**
   - Click **OK**
5. Set **Project language level** to **11**
6. Click **Apply** and **OK**

### Step 4: Import Maven Project (if needed)
1. If you see a popup "Maven projects need to be imported" → Click **Import**
2. Or right-click on `pom.xml` → **Maven → Reload Project**
3. Wait for IntelliJ to download dependencies

### Step 5: Build the Project
1. Click **Build → Build Project** (or press `Ctrl+F9`)
2. Wait for build to complete
3. Check the **Build** panel at bottom for any errors
4. You should see "Build completed successfully"

### Step 6: Run the Main Class
1. In the Project panel, navigate to:
   `src/main/java/com.stockcompare/Main.java`
2. Right-click on `Main.java`
3. Click **Run 'Main.main()'**
4. You should see output in the console

## Expected Output

When you run `Main.java`, you should see:

```
=== Stock-Compare Application ===
Sprint 1 - Architectural Foundations

--- StockData Demo ---
Created: StockData{symbol='AAPL', date=2024-01-15, close=187.30}
Symbol: AAPL
Close Price: $187.3
Volume: 52000000

--- DateRange Validation Demo ---
✓ Valid range: DateRange{2023-01-01 to 2024-01-01}
  Days: 366
✓ Business rule enforced: Date range exceeds maximum of 2 years (730 days)
✓ Validation works: Start date must be before end date

=== Sprint 1 Code Verification Complete ===
All components compiled successfully!
```

## Troubleshooting

### Problem: "Cannot resolve symbol 'StockData'"
**Solution**: 
- Right-click on `src/main/java` folder
- Select **Mark Directory as → Sources Root**

### Problem: "Project SDK is not defined"
**Solution**: Follow Step 3 above to configure SDK

### Problem: "Package com.stockcompare does not exist"
**Solution**: 
- Go to **File → Invalidate Caches / Restart**
- Click **Invalidate and Restart**

### Problem: Maven dependencies not downloading
**Solution**:
- Check internet connection
- Go to **File → Settings → Build, Execution, Deployment → Build Tools → Maven**
- Click **Update** button
- Or run: `mvn clean install` in terminal

### Problem: Code has red underlines
**Solution**:
- Wait for IntelliJ to finish indexing (bottom right corner)
- If still red, try **File → Invalidate Caches**

## Verify Everything Works

Run this checklist:

- [ ] Project opens without errors
- [ ] No red underlines in code
- [ ] Build completes successfully
- [ ] Main.java runs and produces output
- [ ] All interfaces are recognized
- [ ] Package structure is correct

## What This Code Demonstrates (For Code Review)

This Sprint 1 implementation shows:

1. **Domain Models**: `StockData` and `DateRange` with validation
2. **Business Rules**: 2-year maximum enforced in `DateRange`
3. **Interfaces**: All service, data, and presentation layer interfaces defined
4. **Abstract Classes**: Template method pattern in `AbstractStockDataManager`
5. **Exception Handling**: Custom `StockDataException`
6. **Clean Architecture**: Clear separation of layers

All code compiles and demonstrates architectural principles required for Sprint 1.

## For the Code Review (20.2.26)

You can run `Main.java` during the code review to show:
- Code compiles without errors
- Domain models work correctly
- Validation rules are enforced
- Architecture is functional

This proves your implementation meets Sprint 1 requirements.
