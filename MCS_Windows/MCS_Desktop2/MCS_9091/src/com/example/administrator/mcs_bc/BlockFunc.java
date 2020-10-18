package com.example.administrator.mcs_bc;


class BlockFunc
{
	public static Block_t Blockdecider( Block_t block1, Block_t block2)
	{
		Block_t res;
		
		if(block1.time.value < block2.time.value)
		{
			res = block1;
			return res;
		}
		else if(block1.time.value > block2.time.value)
		{
			res = block1;
			return res;
		}
		else
		{
			if(block1.tv.value > block2.tv.value)
			{
				res = block1;
				return res;
			}
			else if (block1.tv.value < block2.tv.value)
			{
				res = block2;
				return res;
			}
			else
			{
				if (block1.num_block_obtain < block2.num_block_obtain)
				{
					res = block1;
					return res;
				}
				else if(block1.num_block_obtain > block2.num_block_obtain)
				{
					res = block2;
					return res;
				}
				else
				{
					if(block1.id.value.compareTo(block2.id.value) <0)
					{
						res = block1;
						return res;
					}
					else 
					{
						res = block2;
						return res;
					}
				}
			}
		}
	}

	
}