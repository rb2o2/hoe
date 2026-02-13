# FEATURE - Introduce coward AI type
create AI type analogous to BerserkAI class named `CowardAI` in `rb2o2.halls.ai` package. 
It should contain chooseManeuver(map: GameMap) method of type (Maneuver, GameObject) which must return:
- if `map` contains at least one hex containing a Hero method should take A) the closest such hex 
to hex which contains class constructor parameter: `entity`, then find B) first content in the hex in opposite to
direction of A) at distance of `entity.charlist.bm` hexes. return (Move, B) GameObject)
- if `map` moes not contain hexes with Hero in contents, return `(DoNothing, entity)`

# FEATURE - Wandering AI type
Create another AI type in `rb2o2.halls.ai` package named WanderingAI. It should be similar in structure to existing AI types. Logic is as follows:
`chooseManeuver(map: GameMap)` method should move to random non occupied neighboring hex, then repeat this -times the bm of entity, so the final return value is (Move, `.head` of contents of the final hex of the random walk)